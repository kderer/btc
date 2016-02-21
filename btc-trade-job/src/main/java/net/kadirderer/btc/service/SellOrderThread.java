package net.kadirderer.btc.service;

import java.util.Calendar;

import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.cancelorder.CancelOrderService;
import net.kadirderer.btc.api.marketdepth.MarketDepthResult;
import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.api.queryorder.QueryOrderResult;
import net.kadirderer.btc.api.queryorder.QueryOrderService;
import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.config.ConfigMap;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.util.email.MailSendService;
import net.kadirderer.btc.util.enumaration.OrderStatus;

public class SellOrderThread implements Runnable {
	
	private QueryOrderService queryOrderService;
	private CancelOrderService cancelOrderService;
	private BuyOrderService buyOrderService;
	private SellOrderService sellOrderService;
	private MarketDepthService marketDepthService;	
	private MailSendService emailSendService;
	
	private String username;
	private String orderId;
	
	public SellOrderThread() {
		
	}
	
	public SellOrderThread(SellOrderService sellOrderService,
			QueryOrderService queryOrderService, CancelOrderService cancelOrderService,
			BuyOrderService buyOrderService, String username, String orderId) {
		super();
		this.queryOrderService = queryOrderService;
		this.cancelOrderService = cancelOrderService;
		this.username = username;	
		this.orderId = orderId;		
	}	

	@Override
	public void run() {
		
		if(ConfigMap.isAutoTradeEnabled()) {
			sellStrategyTwo();
		}
	}
	
	@SuppressWarnings("unused")
	private void sellStrategyOne() {
		try {
			Thread.sleep(ConfigMap.sellOrderCheckPeriod() * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			QueryOrderResult qor = queryOrderService.queryOrder(username, orderId);

			MarketDepthResult mdr = marketDepthService.getMarketDepth(username);

			if (qor.getCompletedAmount() > 0) {				
				double money = qor.getCompletedAmount() * qor.getPrice();
				double price = mdr.getPriceOfMaxBidAmount();
				
				UserOrder order = new UserOrder();
				order.setUsername(username);
				order.setParentId(qor.getUserOrder().getId());
				order.setBasePrice(qor.getPrice());
				order.setPrice(price);
				order.setAmount(money / price);
				
				buyOrderService.buyOrder(order);
			}
			
			cancelOrderService.cancelOrder(username, orderId);
			
			if (qor.getAmount() > 0) {
				UserOrder order = new UserOrder();
				order.setUsername(username);
				order.setParentId(qor.getUserOrder().getParentId());
				order.setBasePrice(qor.getUserOrder().getBasePrice());
				order.setPrice(mdr.getPriceOfMaxAskAmount());
				order.setAmount(qor.getAmount());
				
				sellOrderService.sellOrder(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sellStrategyTwo() {
		
		long startTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = 0;
		long timeOut = ConfigMap.sellOrderTimeLimit() * 1000;		
		QueryOrderResult qor = null;
		MarketDepthResult mdr = null;
		
		double lastCompletedAmount = 0;		
		
		while (timeOut > timeElapsed ) {
			try {
				Thread.sleep(ConfigMap.sellOrderCheckPeriod() * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			try {
				qor = queryOrderService.queryOrder(username, orderId);
				
				if (OrderStatus.CANCELLED.equals(qor.getStatus())) {
					return;
				}
				
				mdr = marketDepthService.getMarketDepth(username);
				
				if (lastCompletedAmount < qor.getCompletedAmount()) {					
					lastCompletedAmount = qor.getCompletedAmount();
				}
				
				if (qor.getOriginalAmount() == qor.getCompletedAmount()) {
					return;
				}
				
				timeElapsed = Calendar.getInstance().getTimeInMillis() - startTime;
			} catch (Exception e) {
				emailSendService.sendMailForException(e);
			}
		}
		
		try {
			cancelOrderService.cancelOrder(username, orderId);
			
			if (qor.getAmount() <= 0) {
				return;
			}
			
			double newPrice = mdr.getLowestAsk();
			
			Thread.sleep(2 * 1000);
			
			UserOrder order = new UserOrder();
			order.setUsername(username);
			order.setParentId(qor.getUserOrder().getParentId());
			order.setBasePrice(qor.getUserOrder().getBasePrice());			
			order.setAmount(qor.getAmount());
			
			if(qor.getPrice() - newPrice > ConfigMap.sellReOrderDelta()) {
				order.setPrice(qor.getPrice() - ConfigMap.sellReOrderDelta());
				sellOrderService.sellOrder(order);
			} else {
				order.setPrice(newPrice);
				sellOrderService.sellOrder(order);
			}
		} catch (Exception e) {
			emailSendService.sendMailForException(e);
		}		
		
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public QueryOrderService getQueryOrderService() {
		return queryOrderService;
	}

	public void setQueryOrderService(QueryOrderService queryOrderService) {
		this.queryOrderService = queryOrderService;
	}

	public CancelOrderService getCancelOrderService() {
		return cancelOrderService;
	}

	public void setCancelOrderService(CancelOrderService cancelOrderService) {
		this.cancelOrderService = cancelOrderService;
	}

	public BuyOrderService getBuyOrderService() {
		return buyOrderService;
	}

	public void setBuyOrderService(BuyOrderService buyOrderService) {
		this.buyOrderService = buyOrderService;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public SellOrderService getSellOrderService() {
		return sellOrderService;
	}

	public void setSellOrderService(SellOrderService sellOrderService) {
		this.sellOrderService = sellOrderService;
	}

	public MarketDepthService getMarketDepthService() {
		return marketDepthService;
	}

	public void setMarketDepthService(MarketDepthService marketDepthService) {
		this.marketDepthService = marketDepthService;
	}

	public MailSendService getEmailSendService() {
		return emailSendService;
	}

	public void setEmailSendService(MailSendService emailSendService) {
		this.emailSendService = emailSendService;
	}
	
}
