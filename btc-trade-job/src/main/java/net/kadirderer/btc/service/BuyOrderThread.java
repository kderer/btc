package net.kadirderer.btc.service;

import java.util.Calendar;

import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.cancelorder.CancelOrderService;
import net.kadirderer.btc.api.marketdepth.MarketDepthResult;
import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.api.queryorder.QueryOrderResult;
import net.kadirderer.btc.api.queryorder.QueryOrderService;
import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.util.configuration.ConfigurationService;
import net.kadirderer.btc.util.email.MailSendService;
import net.kadirderer.btc.util.enumaration.OrderStatus;

public class BuyOrderThread implements Runnable {
	
	private SellOrderService sellOrderService;	
	private QueryOrderService queryOrderService;
	private CancelOrderService cancelOrderService;
	private MarketDepthService marketDepthService;
	private BuyOrderService buyOrderService;
	private String username;
	private String orderId;
	private ConfigurationService cfgService;
	
	private MailSendService emailSendService;
		
	public BuyOrderThread() {
		
	}
	
	public BuyOrderThread(SellOrderService sellOrderService,
			QueryOrderService queryOrderService, CancelOrderService cancelOrderService,
			BuyOrderService buyOrderService, String username, String orderId) {
		super();
		this.sellOrderService = sellOrderService;
		this.queryOrderService = queryOrderService;
		this.cancelOrderService = cancelOrderService;
		this.username = username;	
		this.orderId = orderId;
	}	

	@Override
	public void run() {
		if(cfgService.isAutoTradeEnabled()) {
			strategyTwo();
		}
	}
	
	
	@SuppressWarnings("unused")
	private void strategyOne() {
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			QueryOrderResult qor = queryOrderService.queryOrder(username, orderId, true);

			MarketDepthResult mdr = marketDepthService.getMarketDepth(username);

			if (qor.getCompletedAmount() > 0) {
				UserOrder order = new UserOrder();
				order.setUsername(username);
				order.setParentId(qor.getUserOrder().getId());
				order.setBasePrice(qor.getPrice());
				order.setPrice(mdr.getPriceOfMaxAskAmount());
				order.setAmount(qor.getCompletedAmount());
				
				sellOrderService.sellOrder(order);
			}
			
			cancelOrderService.cancelOrder(username, orderId, false);
			
			if (qor.getAmount() > 0) {
				double balance = qor.getAmount() * qor.getPrice();
				double price = mdr.getPriceOfMaxBidAmount();
				double amount = balance / (price);
				
				Thread.sleep(2 * 1000);
				
				UserOrder order = new UserOrder();
				order.setUsername(username);
				order.setParentId(qor.getUserOrder().getParentId());
				order.setBasePrice(qor.getPrice());
				order.setPrice(price);
				order.setAmount(amount);
				
				buyOrderService.buyOrder(order);
			}							
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void strategyTwo() {
		long startTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = 0;
		long timeOut = cfgService.getBuyOrderTimeLimit() * 1000;		
		QueryOrderResult qor = null;
		MarketDepthResult mdr = null;
		
		double lastCompletedAmount = 0;		
		
		while (timeOut > timeElapsed ) {
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			try {
				qor = queryOrderService.queryOrder(username, orderId, true);
				
				if (OrderStatus.CANCELLED.equals(qor.getStatus())) {
					return;
				}
				
				mdr = marketDepthService.getMarketDepth(username);
				
				if (lastCompletedAmount < qor.getCompletedAmount()) {
					double price = qor.getPrice() + cfgService.getSellOrderDelta();
					
					UserOrder order = new UserOrder();
					order.setUsername(username);
					order.setParentId(qor.getUserOrder().getId());
					order.setBasePrice(qor.getPrice());
					order.setPrice(price);
					order.setAmount(qor.getCompletedAmount() - lastCompletedAmount);
					
					sellOrderService.sellOrder(order);
					
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
			cancelOrderService.cancelOrder(username, orderId, false);
			
			if (qor.getAmount() > 0) {
				double balance = qor.getAmount() * qor.getPrice();
				double price = qor.getPrice() + 0.6;
				
				if (mdr.getHighestBid() < price) {
					price = mdr.getHighestBid();
				}
				
				double amount = balance / (price);
				
				Thread.sleep(2 * 1000);
				
				UserOrder order = new UserOrder();
				order.setUsername(username);
				order.setParentId(qor.getUserOrder().getParentId());
				order.setBasePrice(qor.getPrice());
				order.setPrice(price);
				order.setAmount(amount);
				
				buyOrderService.buyOrder(order);
			}

		} catch (Exception e) {
			emailSendService.sendMailForException(e);
		}
	}
	
	public SellOrderService getSellOrderService() {
		return sellOrderService;
	}

	public void setSellOrderService(SellOrderService sellOrderService) {
		this.sellOrderService = sellOrderService;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public MarketDepthService getMarketDepthService() {
		return marketDepthService;
	}

	public void setMarketDepthService(MarketDepthService marketDepthService) {
		this.marketDepthService = marketDepthService;
	}

	public BuyOrderService getBuyOrderService() {
		return buyOrderService;
	}

	public void setBuyOrderService(BuyOrderService buyOrderService) {
		this.buyOrderService = buyOrderService;
	}

	public MailSendService getEmailSendService() {
		return emailSendService;
	}

	public void setEmailSendService(MailSendService emailSendService) {
		this.emailSendService = emailSendService;
	}
}
