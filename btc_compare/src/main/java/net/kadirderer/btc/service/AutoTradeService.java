package net.kadirderer.btc.service;

import java.util.Calendar;

import net.kadirderer.btc.api.buyorder.BuyOrderResult;
import net.kadirderer.btc.api.cancelorder.CancelOrderResult;
import net.kadirderer.btc.api.queryorder.QueryOrderResult;
import net.kadirderer.btc.api.queryorder.QueryOrderStatus;
import net.kadirderer.btc.api.sellorder.SellOrderResult;
import net.kadirderer.btc.db.model.UserOrder;

public abstract class AutoTradeService {
	
	protected abstract double getHighestBid() throws Exception;
	
	protected abstract double getLowestAsk() throws Exception;
	
	protected abstract UserOrder getLatestPendingBuy(String username) throws Exception;
	
	protected abstract UserOrder getLatestPendingSell(String username) throws Exception;
	
	protected abstract BuyOrderResult buyOrder(String username, double price, double amount) throws Exception;
	
	protected abstract QueryOrderResult queryOrder(String username, String orderId) throws Exception;
	
	protected abstract CancelOrderResult cancelOrder(String username, String orderId) throws Exception;
	
	protected abstract SellOrderResult sellOrder(String username, double price, double amount) throws Exception;
	
	public void autoTrade(String username) throws Exception {
		
		autoBuy(username);
		
		Thread.sleep(1000);
		
		autoSell(username);
		
	}
	
	private void autoSell(String username) throws Exception {
		
		double lowestAsk = getLowestAsk();
		
		UserOrder latestSellOrder = getLatestPendingSell(username);
		if (latestSellOrder != null) {
			QueryOrderResult qor = queryOrder(username, latestSellOrder.getReturnId());
			
			if (qor.getStatus() == QueryOrderStatus.OPEN) {
				if(Calendar.getInstance().getTime().getTime() - latestSellOrder.getCreateDate().getTime() > 1000 * 60 * 10) {
					cancelOrder(username, latestSellOrder.getReturnId());
					sellOrder(username, lowestAsk - 0.001, 0.25);
				}
			} else if (qor.getStatus() == QueryOrderStatus.CLOSED) {
				UserOrder latestBuy = getLatestPendingBuy(username);
				
				if (latestBuy != null) {
					qor = queryOrder(username, latestBuy.getReturnId());
					
					if (qor.getStatus() == QueryOrderStatus.CLOSED) {
						sellOrder(username, lowestAsk - 0.001, latestBuy.getAmount());
					} 
				} else {
					sellOrder(username, lowestAsk - 0.001, 0.25);
				}
			}
		} else {
			sellOrder(username, lowestAsk - 0.001, 0.25);
		}
	}
	
	
	private void autoBuy(String username) throws Exception {
		double highestBid = getHighestBid();
		
		UserOrder latestBuyOrder = getLatestPendingBuy(username);
		if (latestBuyOrder != null) {
			QueryOrderResult qor = queryOrder(username, latestBuyOrder.getReturnId());
			
			if (qor.getStatus() == QueryOrderStatus.OPEN) {
				if(Calendar.getInstance().getTime().getTime() - latestBuyOrder.getCreateDate().getTime() > 1000 * 60 * 10) {
					cancelOrder(username, latestBuyOrder.getReturnId());
					buyOrder(username, highestBid + 0.001, 0.25);
				}
			} else if (qor.getStatus() == QueryOrderStatus.CLOSED) {
				UserOrder latestSell = getLatestPendingSell(username);
				
				if (latestSell != null) {
					qor = queryOrder(username, latestSell.getReturnId());
					
					if (qor.getStatus() == QueryOrderStatus.CLOSED) {
						buyOrder(username, highestBid + 0.001, latestSell.getAmount());				
					} 
				} else {
					buyOrder(username, highestBid + 0.001, 0.25);
				}
			}
		} else {
			buyOrder(username, highestBid + 0.001, 0.25);
		}
	}
	

}
