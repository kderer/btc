package net.kadirderer.btc.impl.buyorder;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.impl.util.NumberUtil;
import net.kadirderer.btc.impl.util.btcc.BtcChinaApiCallable;
import net.kadirderer.btc.impl.util.btcc.BtcChinaPlatformClient;
import net.kadirderer.btc.impl.util.btcc.BtcChinaErrorResult;
import net.kadirderer.btc.util.enumaration.ApiType;
import net.kadirderer.btc.util.enumaration.OrderStatus;
import net.kadirderer.btc.util.enumaration.OrderType;

@Service
public class BtcChinaBuyOrderService implements BuyOrderService, BtcChinaApiCallable {
	
	@Autowired
	private BtcPlatformDao btcPlatformDao;
	
	@Autowired
	private UserOrderDao uoDao;
	
	@Autowired
	private BtcChinaPlatformClient btccClient;
	
	@Override
	@Transactional
	public BtcChinaBuyOrderResult buyOrder(UserOrder order) throws Exception {
		
		if(order.getAmount() < 0.001) {
			BtcChinaErrorResult error = new BtcChinaErrorResult();
			error.setCode("-32008");
			error.setMessage("Invalid amount");
			
			BtcChinaBuyOrderResult result = new BtcChinaBuyOrderResult();
			result.setError(error);
			
			return result;
		}
		
		order.setAmount(NumberUtil.format(order.getAmount()));
		order.setPrice(NumberUtil.format(order.getPrice()));		
		
		String jsonResult = btccClient.callApi(this, order.getUsername(), String.valueOf(order.getPrice()),
				String.valueOf(order.getAmount()));
		
		ObjectMapper om = new ObjectMapper();
		BtcChinaBuyOrderResult result = om.readValue(jsonResult, BtcChinaBuyOrderResult.class);
		
		saveOrder(result, order);

		return result;
	}
	
	private void saveOrder(BtcChinaBuyOrderResult result, UserOrder order) {		
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");		
		
		order.setOrderType(OrderType.BUY.getCode());
		order.setPlatformId(btcChina.getId());
		order.setReturnId(result.getResult());
		if(result.getError() == null) {
			order.setStatus(OrderStatus.PENDING.getCode());
		}
		else {
			order.setStatus(OrderStatus.FAILED.getCode());
		}
		order.setCreateDate(Calendar.getInstance().getTime());
		order.setLastGmobArray(null);
		order.setObrStartTime(null);
		order.setBestGmob(null);
		
		uoDao.save(order);
		
		result.setUserOrderId(order.getId());
	}

	@Override
	public String getApiName() {
		return "buyOrder2";
	}

	@Override
	public String getParameters(String... parameters) {
		return parameters[0] + "," + parameters[1];
	}

	@Override
	public String getJSONRequestParameters(String... parameters) {
		return "\"" + parameters[0] + "\",\"" + parameters[1] + "\"";
	}

	@Override
	public ApiType getApiType() {
		return ApiType.BUYORDER;
	}

}