package net.kadirderer.btc.handler;

import java.util.Calendar;

import net.kadirderer.btc.api.updateorder.UpdateOrderResult;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.impl.buyorder.BtcChinaBuyOrderResult;
import net.kadirderer.btc.impl.cancelorder.BtcChinaCancelOrderResult;
import net.kadirderer.btc.impl.sellorder.BtcChinaSellOrderResult;
import net.kadirderer.btc.impl.updateorder.BtcChinaUpdateOrderResult;
import net.kadirderer.btc.impl.util.NumberUtil;
import net.kadirderer.btc.service.AutoTradeService;
import net.kadirderer.btc.util.StringUtil;
import net.kadirderer.btc.util.configuration.ConfigurationService;
import net.kadirderer.btc.util.email.Email;
import net.kadirderer.btc.util.enumaration.OrderStatus;
import net.kadirderer.btc.util.enumaration.OrderType;

public class OrderEvoluateHandler implements Runnable {
	
	private AutoTradeService autoTradeService;
	private ConfigurationService cfgService;
	private int userOrderId;
	private boolean processing = false;
	
	public static OrderEvoluateHandler evoluate(AutoTradeService autoTradeService, int userOrderId,ConfigurationService cfgService) {
		OrderEvoluateHandler handler = new OrderEvoluateHandler();
		handler.autoTradeService = autoTradeService;
		handler.userOrderId = userOrderId;
		handler.cfgService = cfgService;

		Thread thread = new Thread(handler);
		thread.start();
		
		return handler;
	}
	
	private OrderEvoluateHandler() {
		
	}

	@Override
	public void run() {
		processing = true;
		try {
			UserOrder uo = autoTradeService.findUserOrderById(userOrderId);
			
			if (uo.isAutoTrade() && uo.isAutoUpdate() && (uo.getStatus() == OrderStatus.PENDING.getCode())) {
				double[] maxAndGeometricMeanArray = autoTradeService.getMaxAndGeometricMean(uo.getUsername());
				double highestBid = maxAndGeometricMeanArray[2];
				double gmob = maxAndGeometricMeanArray[3];
				double gmoa = maxAndGeometricMeanArray[1];
				double priceHighestBidDiff = uo.getPrice() - highestBid;
				double lastBidPriceCheckDelta = cfgService.getLastBidPriceCheckDelta();
				double lowestAsk = maxAndGeometricMeanArray[0];						
				
				if (uo.getOrderType() == OrderType.BUY.getCode()) {
					priceHighestBidDiff = highestBid - uo.getPrice();
				}
				
				UpdateOrderResult result = null;
				boolean isThisTheTime = isThisTheTime(uo, gmob, gmoa, highestBid, lowestAsk);
				if (isThisTheTime) {
					double price = highestBid + (lowestAsk - highestBid) / 2.0;
					double amount = uo.getAmount();
					
					if (uo.getOrderType() == OrderType.BUY.getCode()) {
						amount = (uo.getPrice() * amount) / price;
					}
					 
					result = autoTradeService.updateOrder(uo, amount, price);
				}
				else if (lastBidPriceCheckDelta >= priceHighestBidDiff) {
					double price = uo.getPrice();
					double amount = uo.getAmount();
					
					if (uo.getOrderType() == OrderType.BUY.getCode()) {
						price = price - cfgService.getBuyOrderDelta();
						amount = (uo.getPrice() * amount) / price;
					}
					else {
						price = price + cfgService.getSellOrderDelta();
					}
					 
					result = autoTradeService.updateOrder(uo, amount, price);
				}
				
				uo.addGmob(gmob, cfgService.getCheckLastGmobCount());
				uo.addGmoa(gmoa, cfgService.getCheckLastGmobCount());
				
				if (!isThisTheTime && uo.getSecondCpBid() != null) {
					uo.setFirstCpBid(null);
					uo.setSecondCpBid(null);
				}
				
				autoTradeService.saveUserOrder(uo);
				
				if (result != null) {
					evoluateUpdateOrderResult((BtcChinaUpdateOrderResult)result, uo);
				}
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
		
		processing = false;
	}
	
	private boolean isThisTheTime(UserOrder uo, double gmob, double gmoa,
			double highestBid, double lowestAsk) {		
		String[] lastGmobArray = StringUtil.generateArrayFromDeliminatedString('|', uo.getLastGmobArray());
		if (lastGmobArray == null) {
			return false;
		}
		
		String[] lastGmoaArray = StringUtil.generateArrayFromDeliminatedString('|', uo.getLastGmoaArray());
		if (lastGmoaArray == null) {
			return false;
		}
		
		Double lastGmob = NumberUtil.parse(lastGmobArray[0]);		
		if (lastGmob == null) {
			return false;
		}
		
		Double lastGmoa = NumberUtil.parse(lastGmoaArray[0]);		
		if (lastGmoa == null) {
			return false;
		}
		
		int checkLastGmobCount = cfgService.getCheckLastGmobCount();
		if (lastGmobArray.length < checkLastGmobCount) {
			checkLastGmobCount = lastGmobArray.length;
		}
		
		if (NumberUtil.parse(lastGmobArray[checkLastGmobCount - 1]) ==  null) {
			return false;
		}
		
		double profit = (highestBid + (lowestAsk - highestBid) / 2.0) - uo.getBasePrice();
		boolean nonProfitAllowed = cfgService.isNonProfitSellOrderAllowed();
		boolean nonProfitAllowedIfParentHasProfit = cfgService.isNonProfitSellOrderAllowedIfParentHasProfit();
		if (uo.getOrderType() == OrderType.BUY.getCode()) {
			profit = uo.getBasePrice() - (highestBid + (lowestAsk - highestBid) / 2.0);
			nonProfitAllowed = cfgService.isNonProfitBuyOrderAllowed();
			nonProfitAllowedIfParentHasProfit = cfgService.isNonProfitBuyOrderAllowedIfParentHasProfit();
		}	
		
		if (profit < 0.0 && uo.getParentId() != null && nonProfitAllowedIfParentHasProfit) {
			UserOrder parent = autoTradeService.findUserOrderById(uo.getParentId());				
			if (parent != null) {
				double parentProfit = 0.0;
				if (parent.getOrderType() == OrderType.BUY.getCode()) {
					parentProfit = parent.getBasePrice() - parent.getPrice();
				}						
				else if (parent.getOrderType() == OrderType.SELL.getCode()) {
					parentProfit = parent.getPrice() - parent.getBasePrice();
				}
				
				if (parentProfit < 0.0 || -1.0 * profit > parentProfit) {
					return false;
				}
			}
		}
		
		if (profit < 0.0 && uo.getParentId() != null && !nonProfitAllowedIfParentHasProfit && !nonProfitAllowed) {
			return false;
		}
		
		try {
			double product = 1;			
			
			for (String value : lastGmoaArray) {
				product *= NumberUtil.parse(value);
			}			
			double gma = Math.pow(product, 1.0 / lastGmoaArray.length);
			
			product = 1;
			for (String value : lastGmobArray) {
				product *= NumberUtil.parse(value);
			}			
			double gmb = Math.pow(product, 1.0 / lastGmobArray.length);
			
			if (uo.getOrderType() == OrderType.SELL.getCode()) {				
				if (uo.getFirstCpBid() == null && gmb < gmob && gma < gmoa) {
					uo.setFirstCpBid(highestBid);
					return false;
				}
				else if (uo.getFirstCpBid() == null) {
					return false;
				}
				else if (gmb > gmob && gma > gmoa && uo.getPrice() > lowestAsk) {
					if (uo.getSecondCpBid() == null) {
						uo.setSecondCpBid(highestBid);
					}					
				}
				else {
					return false;
				}
			}
			else if (uo.getOrderType() == OrderType.BUY.getCode()) {
				if (uo.getFirstCpBid() == null && gmb > gmob && gma > gmoa) {
					uo.setFirstCpBid(highestBid);
					return false;
				}
				else if (uo.getFirstCpBid() == null) {
					return false;
				}
				else if (gmb < gmob && gma < gmoa && uo.getPrice() < highestBid) {
					if (uo.getSecondCpBid() == null) {
						uo.setSecondCpBid(highestBid);
					}					
				}
				else {					
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
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
				
				Thread.sleep(cfgService.getWaitTimeAfterCancelBuyOrder() * 1000);
				
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
				
				Thread.sleep(cfgService.getWaitTimeAfterCancelSellOrder() * 1000);
				
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
			
			UserOrder order = new UserOrder();
			try {				
				order.setUsername(userOrder.getUsername());
				order.setBasePrice(userOrder.getPrice());
				order.setParentId(userOrder.getId());
				order.setPrice(price);
				order.setAmount(amount);
				order.setAutoUpdate(userOrder.isAutoUpdate());
				order.setAutoTrade(userOrder.isAutoTrade());
				order.setLastGmoaArray(userOrder.getLastGmoaArray());
				order.setLastGmobArray(userOrder.getLastGmobArray());
				
				if (userOrder.isAutoTrade() && userOrder.isAutoUpdate()) {
					order.setStatus(OrderStatus.NEW.getCode());
				}
				
				autoTradeService.sellOrder(order);				
			} catch (Exception e) {
				e.printStackTrace();
				Thread.sleep(cfgService.getWaitTimeAfterCancelBuyOrder() * 1000);
				order.setId(null);
				autoTradeService.buyOrder(order);
			}
		} else {
			double price = userOrder.getPrice() - cfgService.getBuyOrderDelta();
			
			if (!userOrder.isAutoUpdate()) {
				price = userOrder.getPrice() - cfgService.getNonAutoUpdateOrderDelta();
			}
			
			double buyAmount = (userOrder.getPrice() * amount) / price;			
			UserOrder order = new UserOrder();
			try {				
				order.setUsername(userOrder.getUsername());
				order.setBasePrice(userOrder.getPrice());
				order.setParentId(userOrder.getId());
				order.setPrice(price);
				order.setAmount(buyAmount);
				order.setAutoUpdate(userOrder.isAutoUpdate());
				order.setAutoTrade(userOrder.isAutoTrade());
				order.setLastGmoaArray(userOrder.getLastGmoaArray());
				order.setLastGmobArray(userOrder.getLastGmobArray());
				
				if (userOrder.isAutoTrade() && userOrder.isAutoUpdate()) {
					order.setStatus(OrderStatus.NEW.getCode());
				}
				
				autoTradeService.buyOrder(order);				
			} catch (Exception e) {
				e.printStackTrace();
				Thread.sleep(cfgService.getWaitTimeAfterCancelSellOrder() * 1000);
				order.setId(null);
				if (order.isAutoTrade() && order.isAutoUpdate()) {
					order.setStatus(OrderStatus.NEW.getCode());
				}
				autoTradeService.buyOrder(order);
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
			
			try {
				autoTradeService.sendMail(email);
				Thread.sleep(cfgService.getWaitTimeAfterCancelBuyOrder() * 1000);
				order.setId(null);
				if (order.isAutoTrade() && order.isAutoUpdate()) {
					order.setStatus(OrderStatus.NEW.getCode());
				}
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
			
			try {
				autoTradeService.sendMail(email);
				Thread.sleep(cfgService.getWaitTimeAfterCancelSellOrder() * 1000);
				order.setId(null);
				autoTradeService.sellOrder(order);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void evoluateUpdateOrderResult(BtcChinaUpdateOrderResult result, UserOrder order) {
		if (result.getError() != null) {
					
		}
	}
	
	public boolean isProcessing() {
		return processing;
	}
}
