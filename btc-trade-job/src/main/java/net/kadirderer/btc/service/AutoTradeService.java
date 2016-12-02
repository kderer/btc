package net.kadirderer.btc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.api.buyorder.BuyOrderResult;
import net.kadirderer.btc.api.cancelorder.CancelOrderResult;
import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoResult;
import net.kadirderer.btc.api.queryorder.QueryOrderResult;
import net.kadirderer.btc.api.sellorder.SellOrderResult;
import net.kadirderer.btc.api.updateorder.UpdateOrderResult;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.handler.OrderEvoluateHandler;
import net.kadirderer.btc.util.configuration.ConfigurationService;
import net.kadirderer.btc.util.email.Email;

@Service
public abstract class AutoTradeService {
	
	@Autowired
	private ConfigurationService cfgService;
	
	public abstract double getHighestBid() throws Exception;
	
	public abstract double getLowestAsk() throws Exception;
	
	public abstract UserOrder getLatestPendingBuy(String username) throws Exception;
	
	public abstract List<UserOrder> queryPendingAutoTradeOrders(String username) throws Exception;
	
	public abstract UserOrder getLatestPendingSell(String username) throws Exception;
	
	public abstract BuyOrderResult buyOrder(UserOrder order) throws Exception;
	
	public abstract QueryOrderResult queryOrder(String username, String orderId, boolean updateDbStatus) throws Exception;
	
	public abstract CancelOrderResult cancelOrder(String username, String orderId) throws Exception;
	
	public abstract SellOrderResult sellOrder(UserOrder order) throws Exception;
	
	public abstract void sendMailForException(Exception e);
	
	public abstract void sendMail(Email email);
	
	public abstract QueryAccountInfoResult queryAccountInfo(String username) throws Exception;
	
	public abstract UserOrder findUserOrderById(int userOrderId);
	
	public abstract UserOrder saveUserOrder(UserOrder userOrder);
	
	public abstract UpdateOrderResult updateOrder(UserOrder userOrder, double amount, double price) throws Exception;
	
	public abstract double[] getMaxAndGeometricMean(String username) throws Exception;
	
	public void autoTrade(String username) throws Exception {
		List<UserOrder> pendingOrderList = queryPendingAutoTradeOrders(username);
		
		for (UserOrder pendingOrder : pendingOrderList) {
			
			if (pendingOrder.getReturnId() == null || pendingOrder.getReturnId().length() == 0) {
				continue;
			}
			
			queryOrder(username, pendingOrder.getReturnId(), true);
			
			OrderEvoluateHandler.evoluate(this, pendingOrder.getId(), cfgService);
		}
	}	
	
	public void sweep(String username) {
		QueryAccountInfoResult queryAccountInfoResult = null;
		double price = 0;
		
		try {
			queryAccountInfoResult = queryAccountInfo(username);
			price = getLowestAsk();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		double currencyBalance = queryAccountInfoResult.getCurrencyBalance();
		double btcBalance = queryAccountInfoResult.getBtcBalance();
		
		if (btcBalance > 0.5) {
			btcBalance = 0.5;
		}
		
		if (currencyBalance / price > 0.5) {
			currencyBalance = 0.5 * price;
		}
		
		if (btcBalance > 0.05) {
			UserOrder order = new UserOrder();
			order.setUsername(username);
			order.setPrice(price);
			order.setAmount(btcBalance);
			
			try {
				sellOrder(order);
			} catch (Exception e) {
				sendMailForException(e);
			}
		}
		
		if (currencyBalance / price > 0.05) {
			UserOrder order = new UserOrder();
			order.setUsername(username);
			order.setPrice(price);
			order.setAmount(currencyBalance / price);
			
			try {
				buyOrder(order);
			} catch (Exception e) {
				sendMailForException(e);
			}
		}		
	}
}
