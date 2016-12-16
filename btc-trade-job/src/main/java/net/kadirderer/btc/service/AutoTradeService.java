package net.kadirderer.btc.service;

import java.util.Calendar;
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
import net.kadirderer.btc.handler.AutoUpdateHandler;
import net.kadirderer.btc.handler.OrderEvoluateHandler;
import net.kadirderer.btc.util.configuration.ConfigurationService;
import net.kadirderer.btc.util.email.Email;
import net.kadirderer.btc.util.enumaration.OrderStatus;

@Service
public abstract class AutoTradeService {
	
	@Autowired
	private ConfigurationService cfgService;
	
	private boolean firstTime = true;
	
	private long lastAutoTradeCheckTime;
	
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
			if (pendingOrder.getStatus() == OrderStatus.NEW.getCode()) {
				AutoUpdateHandler.handle(this, pendingOrder.getId(), cfgService);
			}
			else if (firstTime && pendingOrder.isAutoUpdate()) { 
				AutoUpdateHandler.handle(this, pendingOrder.getId(), cfgService);
			}
			else if (!pendingOrder.isAutoUpdate() && Calendar.getInstance().getTimeInMillis() - lastAutoTradeCheckTime >= 60000) {
				queryOrder(pendingOrder.getUsername(), pendingOrder.getReturnId(), true);
				
				if (pendingOrder.isAutoTrade()) {
					OrderEvoluateHandler.evoluate(this, pendingOrder.getId(), cfgService);
				}
			}
		}
		
		if (firstTime) {
			firstTime = false;
		}
		
		if (Calendar.getInstance().getTimeInMillis() - lastAutoTradeCheckTime >= 60000) {
			lastAutoTradeCheckTime = Calendar.getInstance().getTimeInMillis();
		}		
	}	
	
	public void sweep(String username) {
			
	}
}
