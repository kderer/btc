package net.kadirderer.btc.impl.queryorder;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.kadirderer.btc.api.queryorder.QueryOrderResult;
import net.kadirderer.btc.api.queryorder.QueryOrderService;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.impl.util.btcc.BtcChinaApiCallable;
import net.kadirderer.btc.impl.util.btcc.BtcChinaPlatformClient;
import net.kadirderer.btc.util.enumaration.ApiType;
import net.kadirderer.btc.util.enumaration.OrderStatus;

@Service
public class BtcChinaQueryOrderService implements QueryOrderService, BtcChinaApiCallable {
	
	@Autowired
	private BtcChinaPlatformClient btccClient;
	
	@Autowired
	private BtcPlatformDao btcPlatformDao;
		
	@Autowired
	private UserOrderDao uoDao;	
	
	@Override
	@Transactional
	public QueryOrderResult queryOrder(String username, String orderId)
			throws Exception {
		
		String jsonResult = btccClient.callApi(this, username, orderId);

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
		
		if (uo.getStatus() == OrderStatus.PENDING.getCode() ||
				uo.getStatus() == OrderStatus.MANUAL.getCode() ||
				uo.getStatus() == OrderStatus.SINGLE.getCode()) {
			
			if (uo.getCompletedAmount() != result.getCompletedAmount()) {
				result.setLastCompletedAmount(result.getCompletedAmount() - uo.getCompletedAmount());
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
		
		result.setUserOrder(uo);
	}

	@Override
	public List<UserOrder> queryPendingAutoTradeOrders(String username) throws Exception {
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");	
		return uoDao.findPending(username, btcChina.getId());
	}

}
