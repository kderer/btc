package net.kadirderer.btc.handler;

import java.util.Calendar;

import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.impl.buyorder.BtcChinaBuyOrderResult;
import net.kadirderer.btc.impl.cancelorder.BtcChinaCancelOrderResult;
import net.kadirderer.btc.impl.sellorder.BtcChinaSellOrderResult;
import net.kadirderer.btc.impl.util.NumberUtil;
import net.kadirderer.btc.service.AutoTradeService;
import net.kadirderer.btc.util.configuration.ConfigurationService;
import net.kadirderer.btc.util.email.Email;
import net.kadirderer.btc.util.enumaration.OrderStatus;
import net.kadirderer.btc.util.enumaration.OrderType;

public class OrderEvoluateHandler implements Runnable {
	
	private AutoTradeService autoTradeService;
	private ConfigurationService cfgService;
	private int userOrderId;
	
	public static void evoluate(AutoTradeService autoTradeService, int userOrderId, ConfigurationService cfgService) {
		OrderEvoluateHandler handler = new OrderEvoluateHandler();
		handler.autoTradeService = autoTradeService;
		handler.userOrderId = userOrderId;
		handler.cfgService = cfgService;

		Thread thread = new Thread(handler);
		thread.start();
	}
	
	private OrderEvoluateHandler() {
		
	}

	@Override
	public void run() {
		try {
			UserOrder uo = autoTradeService.findUserOrderById(userOrderId);
			
			if (uo.isAutoTrade() && uo.isAutoUpdate() && (uo.getStatus() == OrderStatus.PENDING.getCode())) {
				double[] maxAndGeometricMeanArray = autoTradeService.getMaxAndGeometricMean(uo.getUsername());
				double highestBid = maxAndGeometricMeanArray[2];
				double gmob = maxAndGeometricMeanArray[3];
				double priceHighestBidDiff = uo.getPrice() - highestBid;	
				double basePriceHighestBidDiff = uo.getBasePrice() - highestBid;
				double lastBidPriceCheckDelta = cfgService.getLastBidPriceCheckDelta();
//				double lowestAsk = maxAndGeometricMeanArray[0];
				boolean updateBestGmob = false;							
				
				if (uo.getOrderType() == OrderType.BUY.getCode()) {
					priceHighestBidDiff = highestBid - uo.getPrice();
					basePriceHighestBidDiff = highestBid - uo.getBasePrice();
				}
				
				if (lastBidPriceCheckDelta >= priceHighestBidDiff) {
					double price = uo.getPrice();
					double amount = uo.getAmount();
					
					if (uo.getOrderType() == OrderType.BUY.getCode()) {
						price = price - cfgService.getBuyOrderDelta();
						amount = (uo.getPrice() * amount) / price;
					}
					else {
						price = price + cfgService.getSellOrderDelta();
					}
					 
					autoTradeService.updateOrder(uo, amount, price);
					updateBestGmob = true;
				}
				else if (isThisTheTime(uo, gmob, basePriceHighestBidDiff)) {
					double price = highestBid;
					double amount = uo.getAmount();
					
					if (uo.getOrderType() == OrderType.BUY.getCode()) {
						amount = (uo.getPrice() * amount) / price;
					}
					 
					autoTradeService.updateOrder(uo, amount, price);
					updateBestGmob = true;
				}
				
				if (updateBestGmob) {
					uo.setBestGmob(gmob);
					uo.setObrStartTime(null);
				}
				else if (!isLastGmobOBR(uo, gmob)) {
					uo.setObrStartTime(null);
				}
				else if (isLastGmobOBR(uo, gmob) && uo.getObrStartTime() == null) {
					uo.setObrStartTime(Calendar.getInstance().getTimeInMillis());
				}
				
				if (uo.getBestGmob() == null ||
						(uo.getOrderType() == OrderType.SELL.getCode() && gmob > uo.getBestGmob()) ||
						(uo.getOrderType() == OrderType.BUY.getCode() && gmob < uo.getBestGmob())) {
					uo.setBestGmob(gmob);
				}
				
				if (uo.getLastSecondGmob() != null) {
					uo.setLastThirdGmob(uo.getLastSecondGmob());
				}				
				
				if (uo.getLastGmob() != null) {
					uo.setLastSecondGmob(uo.getLastGmob());
				}
				
				uo.setLastGmob(gmob);
				
				autoTradeService.saveUserOrder(uo);
			}
			else if (uo.isAutoTrade() && uo.getStatus() == OrderStatus.DONE.getCode()) {
				createOrderForDoneOrder(uo);
			}
			else if (uo.isAutoTrade() && !uo.isAutoUpdate() && uo.getStatus() == OrderStatus.PENDING.getCode() && isTimeOut(uo)) {
				createOrderForNonAutoUpdateTimeOut(uo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean isThisTheTime(UserOrder uo, double gmob, double basePriceHighestBidDiff) {
		if (uo.getLastGmob() == null || uo.getLastSecondGmob() == null || uo.getLastThirdGmob() == null) {
			return false;
		}		
		
		double product = uo.getLastGmob() * uo.getLastSecondGmob() * uo.getLastThirdGmob();
		double gm = Math.pow(product, 1.0 / 3);
		
		if (uo.getOrderType() == OrderType.SELL.getCode() && gmob < gm) {
			return true;
		}
		else if (uo.getOrderType() == OrderType.BUY.getCode() && gmob > gm) {
			return true;
		}				
		
		return false;
	}
	
	private boolean isLastGmobOBR(UserOrder uo, double lastGmob) {
		if (uo.getBestGmob() == null) {
			return false;
		}
		
		double dif = uo.getBestGmob() - lastGmob;
		if (uo.getOrderType() == OrderType.BUY.getCode()) {
			dif = lastGmob - uo.getBestGmob();
		}
		
		if (dif > cfgService.getBestGmobCheckDelta()) {
			return true;
		}
		
		return false;
	}
	
	
	private boolean isTimeOut(UserOrder userOrder) {
		long timeInMillis = Calendar.getInstance().getTimeInMillis();
		long elapsedTime = timeInMillis - userOrder.getCreateDate().getTime();
		
		int timelimit = cfgService.getBuyOrderTimeLimit() * 1000;
		
		if (userOrder.getOrderType() == OrderType.SELL.getCode()) {
			timelimit = cfgService.getSellOrderTimeLimit() * 1000;
		}
		
		if (!userOrder.isAutoUpdate()) {
			timelimit = cfgService.getNonAutoUpdateTimeLimit() * 1000;
		}
		
		if (elapsedTime < timelimit) {
			return false;
		}
		
		return true;
	}
	
	@SuppressWarnings("unused")
	private boolean canOrderCounterpart(UserOrder order, double highestBid) {
		if (order.getParentId() == null) {
			return false;
		}
		
		UserOrder parent = autoTradeService.findUserOrderById(order.getParentId());
		
		if (order.getOrderType() == OrderType.BUY.getCode() && 
				(parent == null || parent.getStatus() != OrderStatus.CANCELLED.getCode())) {
			return false;
		}
		else if (order.getOrderType() == OrderType.BUY.getCode() && 
				(parent != null && parent.getStatus() == OrderStatus.CANCELLED.getCode())) {
			parent.setPrice(highestBid);
			parent.setAutoTrade(true);
			parent.setAutoUpdate(true);
		}
		else if (order.getOrderType() == OrderType.SELL.getCode() && 
				(parent == null || parent.getStatus() != OrderStatus.CANCELLED.getCode())) {
			parent = new UserOrder();
			parent.setUsername(order.getUsername());
			parent.setAutoTrade(true);
			parent.setAutoUpdate(true);
			parent.setOrderType(OrderType.BUY.getCode());
			parent.setBasePrice(highestBid);
			parent.setPrice(highestBid - cfgService.getBuyOrderDelta());
			parent.setAmount(order.getAmount());
			parent.setParentId(order.getId());
		}
		else if (order.getOrderType() == OrderType.SELL.getCode() &&
				(parent != null && parent.getStatus() == OrderStatus.CANCELLED.getCode())) {
			parent.setPrice(highestBid  - cfgService.getBuyOrderDelta());
			parent.setAmount((parent.getAmount() * parent.getPrice()) / (highestBid - cfgService.getBuyOrderDelta()));
			parent.setAutoTrade(true);
			parent.setAutoUpdate(true);
		}
		
		try {
			if (order.getOrderType() == OrderType.BUY.getCode()) {
				BtcChinaSellOrderResult buyResult = (BtcChinaSellOrderResult)autoTradeService.sellOrder(parent);
				
				if (buyResult.getError() != null) {
					return false;
				}
			}
			else {
				BtcChinaBuyOrderResult buyResult = (BtcChinaBuyOrderResult)autoTradeService.buyOrder(parent);
				if (buyResult.getError() != null) {
					return false;
				}
			}
			
			BtcChinaCancelOrderResult cancelResult = (BtcChinaCancelOrderResult)autoTradeService.cancelOrder(order.getUsername(), order.getReturnId());
			
			if (cancelResult.getError() != null) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;		
	}
	
	private void createOrderForNonAutoUpdateTimeOut(UserOrder userOrder) throws Exception {		
		double highestBid = autoTradeService.getHighestBid();
		double lowestAsk = autoTradeService.getLowestAsk();
		
		if (userOrder.getOrderType() == OrderType.BUY.getCode()) {
			try {
				autoTradeService.cancelOrder(userOrder.getUsername(), userOrder.getReturnId());
				
				double oldCost = userOrder.getPrice() * userOrder.getAmount();
				double price = lowestAsk - cfgService.getNonAutoUpdateOrderDelta();
				
				double amount = oldCost / price;
				
				Thread.sleep(2 * 1000);
				
				UserOrder order = new UserOrder();
				order.setUsername(userOrder.getUsername());
				order.setBasePrice(userOrder.getBasePrice());
				order.setParentId(userOrder.getParentId());
				order.setPrice(price);
				order.setAmount(amount);
				order.setAutoUpdate(userOrder.isAutoUpdate());
				order.setAutoTrade(userOrder.isAutoTrade());
				
				BtcChinaBuyOrderResult result = (BtcChinaBuyOrderResult)autoTradeService.buyOrder(order);							
				evoluateBuyOrderResult(result, order);		
			} catch (Exception e) {
				autoTradeService.sendMailForException(e);
			}			
		} else {
			try {
				autoTradeService.cancelOrder(userOrder.getUsername(), userOrder.getReturnId());
				double price = highestBid + cfgService.getNonAutoUpdateOrderDelta();
				
				Thread.sleep(2 * 1000);
				
				UserOrder order = new UserOrder();
				order.setUsername(userOrder.getUsername());
				order.setBasePrice(userOrder.getBasePrice());
				order.setParentId(userOrder.getParentId());
				order.setPrice(price);
				order.setAmount(userOrder.getAmount());
				order.setAutoUpdate(userOrder.isAutoUpdate());
				order.setAutoTrade(userOrder.isAutoTrade());
				
				BtcChinaSellOrderResult result = (BtcChinaSellOrderResult)autoTradeService.sellOrder(order);
				evoluateSellOrderResult(result, order);
			} catch (Exception e) {
				autoTradeService.sendMailForException(e);
			}
		}
	}
	
	private void createOrderForDoneOrder(UserOrder userOrder) throws Exception {
		double amount = userOrder.getAmount();
		
		if (userOrder.getOrderType() == OrderType.BUY.getCode()) {
			double price = userOrder.getPrice() + cfgService.getSellOrderDelta();
			
			if (!userOrder.isAutoUpdate()) {
				price = userOrder.getPrice() + cfgService.getNonAutoUpdateOrderDelta();
			}
			
			try {
				UserOrder order = new UserOrder();
				order.setUsername(userOrder.getUsername());
				order.setBasePrice(userOrder.getPrice());
				order.setParentId(userOrder.getId());
				order.setPrice(price);
				order.setAmount(amount);
				order.setAutoUpdate(userOrder.isAutoUpdate());
				order.setAutoTrade(userOrder.isAutoTrade());
				
				autoTradeService.sellOrder(order);				
			} catch (Exception e) {
				autoTradeService.sendMailForException(e);
			}
		} else {
			double price = userOrder.getPrice() - cfgService.getBuyOrderDelta();
			
			if (!userOrder.isAutoUpdate()) {
				price = userOrder.getPrice() - cfgService.getNonAutoUpdateOrderDelta();
			}
			
			double buyAmount = NumberUtil.format(userOrder.getPrice() * amount / price);			
			try {				
				UserOrder order = new UserOrder();
				order.setUsername(userOrder.getUsername());
				order.setBasePrice(userOrder.getPrice());
				order.setParentId(userOrder.getId());
				order.setPrice(price);
				order.setAmount(buyAmount);
				order.setAutoUpdate(userOrder.isAutoUpdate());
				order.setAutoTrade(userOrder.isAutoTrade());
				
				autoTradeService.buyOrder(order);				
			} catch (Exception e) {
				autoTradeService.sendMailForException(e);
			}
		}
	}
	
	private void evoluateBuyOrderResult(BtcChinaBuyOrderResult result, UserOrder order) {
		if (result.getError() != null) {
			Email email = new Email();
			email.addToToList("kderer@hotmail.com");
			email.setSubject("BTC Buy Order Error");
			email.setFrom("error@btc.kadirderer.net");
			email.setBody(result.getError().getCode() + "\n" + result.getError().getMessage());
			
			autoTradeService.sendMail(email);
			
			try {
				Thread.sleep(3 * 1000);
				order.setId(null);
				autoTradeService.buyOrder(order);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void evoluateSellOrderResult(BtcChinaSellOrderResult result, UserOrder order) {
		if (result.getError() != null) {
			Email email = new Email();
			email.addToToList("kderer@hotmail.com");
			email.setSubject("BTC Sell Order Error");
			email.setFrom("error@btc.kadirderer.net");
			email.setBody(result.getError().getCode() + "\n" + result.getError().getMessage());
			
			autoTradeService.sendMail(email);
			
			try {
				Thread.sleep(3 * 1000);
				order.setId(null);
				autoTradeService.sellOrder(order);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
