package net.kadirderer.btc.api.cancelorder;

public interface CancelOrderService {
	
	public CancelOrderResult cancelOrder(String username, String orderId, boolean forUpdate) throws Exception;

}
