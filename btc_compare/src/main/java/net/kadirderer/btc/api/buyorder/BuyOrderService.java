package net.kadirderer.btc.api.buyorder;

public interface BuyOrderService {
	
	public BuyOrderResult buyOrder(String username, double price, double amount) throws Exception;

}
