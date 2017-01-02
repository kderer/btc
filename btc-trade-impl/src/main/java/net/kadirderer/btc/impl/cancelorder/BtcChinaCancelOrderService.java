package net.kadirderer.btc.impl.cancelorder;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.kadirderer.btc.api.cancelorder.CancelOrderResult;
import net.kadirderer.btc.api.cancelorder.CancelOrderService;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.impl.util.btcc.BtcChinaApiCallable;
import net.kadirderer.btc.impl.util.btcc.BtcChinaPlatformClient;
import net.kadirderer.btc.util.enumaration.ApiType;
import net.kadirderer.btc.util.enumaration.OrderStatus;

@Service
public class BtcChinaCancelOrderService implements CancelOrderService, BtcChinaApiCallable {

	@Autowired
	private BtcPlatformDao btcPlatformDao;
	
	@Autowired
	private UserOrderDao uoDao;
	
	@Autowired
	private BtcChinaPlatformClient btccClient;
	
	@Override
	@Transactional
	public CancelOrderResult cancelOrder(String username, String orderId, boolean forUpdate)
			throws Exception {		
		String jsonResult = btccClient.callApi(this, username, orderId);

		ObjectMapper om = new ObjectMapper();
		BtcChinaCancelOrderResult result = om.readValue(jsonResult, BtcChinaCancelOrderResult.class);
		
		updateOrder(username, orderId, result, forUpdate);	
		
		return result;
	}
	
	
	private void updateOrder(String username, String orderId, BtcChinaCancelOrderResult result, boolean forUpdate) {		
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");		
		UserOrder uo = uoDao.findByOrderId(username, btcChina.getId(), orderId);
		
		if (result.getError() == null) {
			uo.setStatus(OrderStatus.CANCELLED.getCode());
		}
		else if ("-32025".equals(result.getError().getCode())) {
			uo.setStatus(OrderStatus.CANCELLED.getCode());
		}
		else if ("-32026".equals(result.getError().getCode())) {
			// Already completed.
			// Do nothing here, converting status to done while canceling breaks the
			// auto trade cycle
		}
		else {
			uo.setStatus(OrderStatus.CANCEL_FAILED.getCode());
		}
		
		if (forUpdate) {
			uo.setStatus(OrderStatus.UPDATING.getCode());
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
