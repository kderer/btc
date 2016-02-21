package net.kadirderer.btc.service;

import java.util.Calendar;
import java.util.List;

import net.kadirderer.btc.api.buyorder.BuyOrderResult;
import net.kadirderer.btc.api.cancelorder.CancelOrderResult;
import net.kadirderer.btc.api.queryorder.QueryOrderResult;
import net.kadirderer.btc.api.queryorder.QueryOrderStatus;
import net.kadirderer.btc.api.sellorder.SellOrderResult;
import net.kadirderer.btc.config.ConfigMap;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.impl.buyorder.BtcChinaBuyOrderResult;
import net.kadirderer.btc.impl.sellorder.BtcChinaSellOrderResult;
import net.kadirderer.btc.impl.util.NumberUtil;
import net.kadirderer.btc.util.email.Email;
import net.kadirderer.btc.util.enumaration.OrderStatus;
import net.kadirderer.btc.util.enumaration.OrderType;

public abstract class AutoTradeService {
	
	protected abstract double getHighestBid() throws Exception;
	
	protected abstract double getLowestAsk() throws Exception;
	
	protected abstract UserOrder getLatestPendingBuy(String username) throws Exception;
	
	protected abstract List<UserOrder> queryPendingAutoTradeOrders(String username) throws Exception;
	
	protected abstract UserOrder getLatestPendingSell(String username) throws Exception;
	
	protected abstract BuyOrderResult buyOrder(UserOrder order) throws Exception;
	
	protected abstract QueryOrderResult queryOrder(String username, String orderId) throws Exception;
	
	protected abstract CancelOrderResult cancelOrder(String username, String orderId) throws Exception;
	
	protected abstract SellOrderResult sellOrder(UserOrder order) throws Exception;
	
	protected abstract void sendMailForException(Exception e);
	
	protected abstract void sendMail(Email email);
	
	public void autoTrade(String username) throws Exception {
		List<UserOrder> pendingOrderList = queryPendingAutoTradeOrders(username);
		
		for (UserOrder pendingOrder : pendingOrderList) {
			
			QueryOrderResult queryOrderResult = queryOrder(username, pendingOrder.getReturnId());
			
			if (pendingOrder.getStatus() == OrderStatus.MANUAL.getCode()) {
				continue;
			}
			
			double lastCompletedAmount = queryOrderResult.getLastCompletedAmount(); 
			
			if (lastCompletedAmount > 0) {
				createOrderForCompletedAmount(pendingOrder, lastCompletedAmount);
			}
			
			if (queryOrderResult.getStatus() == QueryOrderStatus.PENDING
					|| queryOrderResult.getStatus() == QueryOrderStatus.OPEN) {
				checkTimeOut(pendingOrder);
			}					
		}
	}
	
	private void createOrderForCompletedAmount(UserOrder userOrder, double amount) {
		if (userOrder.getOrderType() == OrderType.BUY.getCode()) {
			double price = userOrder.getPrice() + ConfigMap.sellOrderDelta();
			try {
				UserOrder order = new UserOrder();
				order.setUsername(userOrder.getUsername());
				order.setBasePrice(userOrder.getPrice());
				order.setParentId(userOrder.getId());
				order.setPrice(price);
				order.setAmount(amount);
				
				sellOrder(order);
			} catch (Exception e) {
				sendMailForException(e);
			}
		} else {
			double price = userOrder.getPrice() - ConfigMap.buyOrderDelta();
			double buyAmount = NumberUtil.format(userOrder.getPrice() * amount / price);			
			try {				
				UserOrder order = new UserOrder();
				order.setUsername(userOrder.getUsername());
				order.setBasePrice(userOrder.getPrice());
				order.setParentId(userOrder.getId());
				order.setPrice(price);
				order.setAmount(buyAmount);
				
				buyOrder(order);
			} catch (Exception e) {
				sendMailForException(e);
			}
		}
	}
	
	public void checkTimeOut(UserOrder userOrder) {
		long timeInMillis = Calendar.getInstance().getTimeInMillis();
		long elapsedTime = timeInMillis - userOrder.getCreateDate().getTime();
		
		if (userOrder.getOrderType() == OrderType.BUY.getCode()
				&& elapsedTime >= ConfigMap.buyOrderTimeLimit() * 1000) {
			try {
				cancelOrder(userOrder.getUsername(), userOrder.getReturnId());
				double oldCost = userOrder.getPrice() * userOrder.getAmount();
				double price = userOrder.getPrice() + ConfigMap.buyReOrderDelta();
				double amount = NumberUtil.format(oldCost / price);
				
				UserOrder order = new UserOrder();
				order.setUsername(userOrder.getUsername());
				order.setBasePrice(userOrder.getBasePrice());
				order.setParentId(userOrder.getParentId());
				order.setPrice(price);
				order.setAmount(amount);
				
				BtcChinaBuyOrderResult result = (BtcChinaBuyOrderResult)buyOrder(order);
				evoluateBuyOrderResult(result);				
			} catch (Exception e) {
				sendMailForException(e);
			}			
		} else if (elapsedTime >= ConfigMap.sellOrderTimeLimit() * 1000) {
			try {
				cancelOrder(userOrder.getUsername(), userOrder.getReturnId());
				double price = userOrder.getPrice() - ConfigMap.sellReOrderDelta();
				
				UserOrder order = new UserOrder();
				order.setUsername(userOrder.getUsername());
				order.setBasePrice(userOrder.getBasePrice());
				order.setParentId(userOrder.getParentId());
				order.setPrice(price);
				order.setAmount(userOrder.getAmount());
				
				BtcChinaSellOrderResult result = (BtcChinaSellOrderResult)sellOrder(order);
				evoluateSellOrderResult(result);
			} catch (Exception e) {
				sendMailForException(e);
			}
		}
	}
	
	public void evoluateBuyOrderResult(BtcChinaBuyOrderResult result) {
		if (result.getError() != null) {
			Email email = new Email();
			email.addToToList("kderer@hotmail.com");
			email.setSubject("BTC Sell Order Error");
			email.setFrom("exception@btc.kadirderer.net");
			email.setBody(result.getError().getCode() + "\n" + result.getError().getMessage());
			
			sendMail(email);
		}
	}
	
	public void evoluateSellOrderResult(BtcChinaSellOrderResult result) {
		if (result.getError() != null) {
			Email email = new Email();
			email.addToToList("kderer@hotmail.com");
			email.setSubject("BTC Sell Order Error");
			email.setFrom("exception@btc.kadirderer.net");
			email.setBody(result.getError().getCode() + "\n" + result.getError().getMessage());
			
			sendMail(email);
		}
	}
	

}
