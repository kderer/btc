package net.kadirderer.btc.api.queryorder;

import java.util.List;

import net.kadirderer.btc.db.model.UserOrder;

public interface QueryOrderService {
	
	public QueryOrderResult queryOrder(String username, String orderId) throws Exception;
	
	public List<UserOrder> queryPendingAutoTradeOrders(String username) throws Exception;

}
