package net.kadirderer.btc.api.sellorder;


public interface SellOrderService {
	
	public SellOrderResult sellOrder(String username, double price, double amount, double basePrice) throws Exception;
	
	public double getProfit(String username);
	
	public void addProfit(String orderId, double profit); 

}
