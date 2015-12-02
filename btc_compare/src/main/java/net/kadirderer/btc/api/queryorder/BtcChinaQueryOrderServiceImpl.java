package net.kadirderer.btc.api.queryorder;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.kadirderer.btc.api.ApiType;
import net.kadirderer.btc.api.OrderStatus;
import net.kadirderer.btc.api.util.btcchina.BtcChinaApiCallable;
import net.kadirderer.btc.api.util.btcchina.BtcChinaApiUtil;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.UserOrder;

@Service
public class BtcChinaQueryOrderServiceImpl implements QueryOrderService, BtcChinaApiCallable {
	
	@Autowired
	private BtcPlatformDao btcPlatformDao;
		
	@Autowired
	private UserOrderDao uoDao;	
	
	@Override
	@Transactional
	public QueryOrderResult queryOrder(String username, String orderId)
			throws Exception {
		
		String jsonResult = BtcChinaApiUtil.callApi(this, username, orderId);

		ObjectMapper om = new ObjectMapper();
		BtcChinaQueryOrderResult result = om.readValue(jsonResult, BtcChinaQueryOrderResult.class);
		
		if (result.getError() == null) {
			updateOrder(username, orderId, result);
		}
		
		return result;
	}

	@Override
	public String getApiName() {
		return "getOrder";
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
		return ApiType.QUERYORDER;
	}
	
	private void updateOrder(String username, String orderId, BtcChinaQueryOrderResult result) {
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");
		
		UserOrder uo = uoDao.findByOrderId(username, btcChina.getId(), orderId);
		
		if (uo.getStatus() == OrderStatus.PENDING.getCode()) {
			if (uo.getCompletedAmount() != result.getCompletedAmount()) {
				uo.setCompletedAmount(result.getCompletedAmount());
			}
			
			switch (result.getStatus()) {
				case CANCELLED:
					uo.setStatus(OrderStatus.CANCELLED.getCode());
					uo.setUpdateDate(Calendar.getInstance().getTime());
				case ERROR:
				case INSUFFICIENT_BALANCE :
					uo.setStatus(OrderStatus.FAILED.getCode());
					uo.setUpdateDate(Calendar.getInstance().getTime());
				case CLOSED:
					uo.setStatus(OrderStatus.DONE.getCode());
					uo.setUpdateDate(Calendar.getInstance().getTime());
				default:
					break;
			}
		}
		
	}

}
