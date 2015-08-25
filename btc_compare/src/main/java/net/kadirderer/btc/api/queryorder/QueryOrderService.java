package net.kadirderer.btc.api.queryorder;

public interface QueryOrderService {
	
	public QueryOrderResult queryOrder(String username, String orderId) throws Exception;

}
