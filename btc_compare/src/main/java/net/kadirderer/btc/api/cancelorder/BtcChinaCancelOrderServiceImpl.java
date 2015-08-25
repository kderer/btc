package net.kadirderer.btc.api.cancelorder;

import java.util.Calendar;

import net.kadirderer.btc.api.ApiType;
import net.kadirderer.btc.api.OrderStatus;
import net.kadirderer.btc.api.util.btcchina.BtcChinaApiCallable;
import net.kadirderer.btc.api.util.btcchina.BtcChinaApiUtil;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.UserOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BtcChinaCancelOrderServiceImpl implements CancelOrderService, BtcChinaApiCallable {

	@Autowired
	private BtcPlatformDao btcPlatformDao;
	
	@Autowired
	private UserOrderDao uoDao;
	
	@Override
	@Transactional
	public CancelOrderResult cancelOrder(String username, String orderId)
			throws Exception {		
		String jsonResult = BtcChinaApiUtil.callApi(this, username, orderId);

		ObjectMapper om = new ObjectMapper();
		BtcChinaCancelOrderResult result = om.readValue(jsonResult, BtcChinaCancelOrderResult.class);
		
		updateOrder(username, orderId, result);	
		
		return result;
	}
	
	
	private void updateOrder(String username, String orderId, BtcChinaCancelOrderResult result) {		
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");		
		UserOrder uo = uoDao.findByOrderId(username, btcChina.getId(), orderId);
		
		if (result.getError() == null) {
			uo.setStatus(OrderStatus.CANCELLED.getCode());
		} else if ("-32025".equals(result.getError().getCode())) {
			uo.setStatus(OrderStatus.CANCELLED.getCode());
		} else if ("-32026".equals(result.getError().getCode())) {
			uo.setStatus(OrderStatus.DONE.getCode());
		}
		
		uo.setUpdateDate(Calendar.getInstance().getTime());		
	}


	@Override
	public String getApiName() {
		return "cancelOrder";
	}


	@Override
	public String getParameters(String... parameters) {
		return parameters[0];
	}


	@Override
	public String getJSONRequestParameters(String... parameters) {
		return parameters[0];
	}


	@Override
	public ApiType getApiType() {
		return ApiType.CANCELORDER;
	}

}
