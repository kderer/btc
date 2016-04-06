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
	
	protected abstract void updatePartnerts(int userOrderId, int parnertUserOrderId);
	
	protected abstract void sendMailForException(Exception e);
	
	protected abstract void sendMail(Email email);
	
	protected abstract UserOrder findPendingPartner(int userOrderId);
	
	protected abstract void updatePartnerIdWithNewId(int oldUserOrderId, int newUserOrderId);
	
	public void autoTrade(String username) throws Exception {
		List<UserOrder> pendingOrderList = queryPendingAutoTradeOrders(username);
		
		for (UserOrder pendingOrder : pendingOrderList) {
			
			if (pendingOrder.getReturnId() == null || pendingOrder.getReturnId().length() == 0) {
				continue;
			}
			
			char tempStatus = pendingOrder.getStatus();
			
			QueryOrderResult queryOrderResult = queryOrder(username, pendingOrder.getReturnId());
			
			if (tempStatus == OrderStatus.MANUAL.getCode()) {
				continue;
			}
			
			pendingOrder.setCompletedAmount(queryOrderResult.getCompletedAmount());
			
			double lastCompletedAmount = queryOrderResult.getLastCompletedAmount(); 
			
			if (lastCompletedAmount > 0) {
				UserOrder partner = findPendingPartner(pendingOrder.getId());
				if (partner != null) {
					cancelOrder(partner.getUsername(), partner.getReturnId());
				}
				
				createOrdersForCompletedAmount(pendingOrder, partner, lastCompletedAmount);
			}
			
			if (queryOrderResult.getStatus() == QueryOrderStatus.PENDING
					|| queryOrderResult.getStatus() == QueryOrderStatus.OPEN) {
				checkTimeOut(pendingOrder, pendingOrderList);
			}					
		}
	}
	
	private void createOrdersForCompletedAmount(UserOrder userOrder, UserOrder _partnerOrder,
			double amount) throws Exception {
		if (userOrder.getOrderType() == OrderType.BUY.getCode()) {
			double price = userOrder.getPrice() + ConfigMap.sellOrderDelta();
			try {
				UserOrder order = new UserOrder();
				order.setUsername(userOrder.getUsername());
				order.setBasePrice(userOrder.getPrice());
				order.setParentId(userOrder.getId());
				order.setPrice(price);
				order.setAmount(amount);
				
				SellOrderResult soResult = sellOrder(order);
				
				UserOrder partnerOrder = new UserOrder();
				partnerOrder.setUsername(userOrder.getUsername());
				partnerOrder.setPrice(userOrder.getPrice() - ConfigMap.buyOrderDelta());
				partnerOrder.setAmount(amount);
				
				BuyOrderResult boResult = buyOrder(partnerOrder);
				
				updatePartnerts(soResult.getUserOrderId(), boResult.getUserOrderId());
				
				createPartnerForRemainingAmount(userOrder, _partnerOrder);				
			} catch (Exception e) {
				sendMailForException(e);
			}
		} else {
			double price = getLowestAsk();
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
	
	private void checkTimeOut(UserOrder userOrder, List<UserOrder> pendingOrders) {
		long timeInMillis = Calendar.getInstance().getTimeInMillis();
		long elapsedTime = timeInMillis - userOrder.getCreateDate().getTime();
		
		if (userOrder.getOrderType() == OrderType.BUY.getCode()
				&& elapsedTime >= ConfigMap.buyOrderTimeLimit() * 1000) {
			try {
				cancelOrder(userOrder.getUsername(), userOrder.getReturnId());
				double oldCost = userOrder.getPrice() * userOrder.getAmount();
				double price = userOrder.getPrice() + ConfigMap.buyReOrderDelta();
				double amount = NumberUtil.format(oldCost / price);
				
				Thread.sleep(2 * 1000);
				
				UserOrder order = new UserOrder();
				order.setUsername(userOrder.getUsername());
				order.setBasePrice(userOrder.getBasePrice());
				order.setParentId(userOrder.getParentId());
				order.setPrice(price);
				order.setAmount(amount);
				order.setPartnerId(userOrder.getPartnerId());
				
				BtcChinaBuyOrderResult result = (BtcChinaBuyOrderResult)buyOrder(order);
				updatePartnerId(pendingOrders, userOrder.getId(), result.getUserOrderId());
				updatePartnerIdWithNewId(userOrder.getId(), result.getUserOrderId());				
				evoluateBuyOrderResult(result);		
			} catch (Exception e) {
				sendMailForException(e);
			}			
		} else if (elapsedTime >= ConfigMap.sellOrderTimeLimit() * 1000) {
			try {
				cancelOrder(userOrder.getUsername(), userOrder.getReturnId());
				double price = userOrder.getPrice() - ConfigMap.sellReOrderDelta();
				
				Thread.sleep(2 * 1000);
				
				UserOrder order = new UserOrder();
				order.setUsername(userOrder.getUsername());
				order.setBasePrice(userOrder.getBasePrice());
				order.setParentId(userOrder.getParentId());
				order.setPrice(price);
				order.setAmount(userOrder.getAmount());
				order.setPartnerId(userOrder.getPartnerId());
				
				BtcChinaSellOrderResult result = (BtcChinaSellOrderResult)sellOrder(order);
				updatePartnerId(pendingOrders, userOrder.getId(), result.getUserOrderId());
				updatePartnerIdWithNewId(userOrder.getId(), result.getUserOrderId());
				evoluateSellOrderResult(result);
			} catch (Exception e) {
				sendMailForException(e);
			}
		}
	}
	
	private void createPartnerForRemainingAmount(UserOrder order, UserOrder partnerOrder)
			throws Exception {
		if (partnerOrder == null) {
			return;
		}
		
		double remainingAmount = order.getAmount() - order.getCompletedAmount();
		
		if (remainingAmount > 0.001) {
			UserOrder newPartner = new UserOrder();
			newPartner.setUsername(partnerOrder.getUsername());
			newPartner.setBasePrice(partnerOrder.getPrice());
			newPartner.setParentId(partnerOrder.getId());
			newPartner.setPrice(partnerOrder.getPrice());
			newPartner.setAmount(remainingAmount);
			
			SellOrderResult soResult = sellOrder(newPartner);
			
			updatePartnerts(order.getId(), soResult.getUserOrderId());
		}
	}
	
	private void updatePartnerId(List<UserOrder> pendingOrderList, int oldPartnerId, int newPartnerId) {
		if (pendingOrderList == null || pendingOrderList.size() == 0) {
			return;
		}
		
		for (UserOrder order : pendingOrderList) {
			if (order != null && order.getPartnerId() == oldPartnerId) {
				order.setPartnerId(newPartnerId);
			}
		}
	}
	
	private void evoluateBuyOrderResult(BtcChinaBuyOrderResult result) {
		if (result.getError() != null) {
			Email email = new Email();
			email.addToToList("kderer@hotmail.com");
			email.setSubject("BTC Buy Order Error");
			email.setFrom("error@btc.kadirderer.net");
			email.setBody(result.getError().getCode() + "\n" + result.getError().getMessage());
			
			sendMail(email);
		}
	}
	
	private void evoluateSellOrderResult(BtcChinaSellOrderResult result) {
		if (result.getError() != null) {
			Email email = new Email();
			email.addToToList("kderer@hotmail.com");
			email.setSubject("BTC Sell Order Error");
			email.setFrom("error@btc.kadirderer.net");
			email.setBody(result.getError().getCode() + "\n" + result.getError().getMessage());
			
			sendMail(email);
		}
	}
	

}
