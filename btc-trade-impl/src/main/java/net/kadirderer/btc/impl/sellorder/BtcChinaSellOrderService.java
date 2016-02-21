package net.kadirderer.btc.impl.sellorder;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.impl.util.NumberUtil;
import net.kadirderer.btc.impl.util.btcc.BtcChinaApiCallable;
import net.kadirderer.btc.impl.util.btcc.BtcChinaErrorResult;
import net.kadirderer.btc.impl.util.btcc.BtcChinaPlatformClient;
import net.kadirderer.btc.util.enumaration.ApiType;
import net.kadirderer.btc.util.enumaration.OrderStatus;
import net.kadirderer.btc.util.enumaration.OrderType;

@Service
public class BtcChinaSellOrderService implements SellOrderService, BtcChinaApiCallable {

	@Autowired
	private BtcPlatformDao btcPlatformDao;
	
	@Autowired
	private UserOrderDao uoDao;
	
	@Autowired
	private BtcChinaPlatformClient btccClient;

	@Override
	@Transactional
	public BtcChinaSellOrderResult sellOrder(UserOrder order) throws Exception {
		
		order.setAmount(NumberUtil.format(order.getAmount()));
		order.setPrice(NumberUtil.format(order.getPrice()));
		
		if(order.getAmount() < 0.001) {
			BtcChinaErrorResult error = new BtcChinaErrorResult();
			error.setCode("-32008");
			error.setMessage("Amount too small");
			
			BtcChinaSellOrderResult result = new BtcChinaSellOrderResult();
			result.setError(error);
			
			return result;
		}
		
		String jsonResult = btccClient.callApi(this, order.getUsername(), String.valueOf(order.getPrice()),
				String.valueOf(order.getAmount()));
		
		ObjectMapper om = new ObjectMapper();
		BtcChinaSellOrderResult result = om.readValue(jsonResult, BtcChinaSellOrderResult.class);
		
		saveOrder(result, order);
		
		return result;
	}
	
	private void saveOrder(BtcChinaSellOrderResult result, UserOrder order) {		
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");
		
		order.setOrderType(OrderType.SELL.getCode());
		order.setPlatformId(btcChina.getId());
		order.setReturnId(result.getResult());
		if(result.getError() == null && order.getStatus() != OrderStatus.MANUAL.getCode()) {
			order.setStatus(OrderStatus.PENDING.getCode());
		}
		else if (order.getStatus() != OrderStatus.MANUAL.getCode()) {
			order.setStatus(OrderStatus.FAILED.getCode());
		}
		order.setCreateDate(Calendar.getInstance().getTime());
		
		uoDao.save(order);
	}

	@Override
	public String getApiName() {
		return "sellOrder2";
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
		return ApiType.SELLORDER;
	}

}