package net.kadirderer.btc.service;

import java.util.Calendar;

import net.kadirderer.btc.api.OrderStatus;
import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.cancelorder.CancelOrderService;
import net.kadirderer.btc.api.marketdepth.MarketDepthResult;
import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.api.queryorder.QueryOrderResult;
import net.kadirderer.btc.api.queryorder.QueryOrderService;
import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.config.ConfigMap;

public class BuyOrderThread implements Runnable {
	
	private SellOrderService sellOrderService;	
	private QueryOrderService queryOrderService;
	private CancelOrderService cancelOrderService;
	private MarketDepthService marketDepthService;
	private BuyOrderService buyOrderService;
	private String username;
	private String orderId;
		
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
		if(ConfigMap.isAutoTradeEnabled()) {
			strategyTwo();
		}
	}
	
	
	@SuppressWarnings("unused")
	private void strategyOne() {
		try {
			Thread.sleep(ConfigMap.buyOrderCheckPeriod() * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			QueryOrderResult qor = queryOrderService.queryOrder(username, orderId);

			MarketDepthResult mdr = marketDepthService.getMarketDepth(username);

			if (qor.getCompletedAmount() > 0) {
				sellOrderService.sellOrder(username, mdr.getPriceOfMaxAskAmount(), qor.getCompletedAmount(), qor.getPrice());
			}
			
			cancelOrderService.cancelOrder(username, orderId);
			
			if (qor.getAmount() > 0) {
				double balance = qor.getAmount() * qor.getPrice();
				double price = mdr.getPriceOfMaxBidAmount();
				double amount = balance / (price);
				
				Thread.sleep(2 * 1000);
				
				buyOrderService.buyOrder(username, price, amount);
			}							
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void strategyTwo() {
		long startTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = 0;
		long timeOut = ConfigMap.buyOrderTimeLimit() * 1000;		
		QueryOrderResult qor = null;
		MarketDepthResult mdr = null;
		
		double lastCompletedAmount = 0;		
		
		while (timeOut > timeElapsed ) {
			try {
				Thread.sleep(ConfigMap.buyOrderCheckPeriod() * 1000);
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
					double price = qor.getPrice() + ConfigMap.sellOrderDelta();
					sellOrderService.sellOrder(username, price, qor.getCompletedAmount() - lastCompletedAmount, qor.getPrice());
					
					lastCompletedAmount = qor.getCompletedAmount();
				}
				
				if (qor.getOriginalAmount() == qor.getCompletedAmount()) {
					return;
				}
				
				timeElapsed = Calendar.getInstance().getTimeInMillis() - startTime;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			cancelOrderService.cancelOrder(username, orderId);
			
			if (qor.getAmount() > 0) {
				double balance = qor.getAmount() * qor.getPrice();
				double price = qor.getPrice() + ConfigMap.buyReOrderDelta();
				
				if (mdr.getHighestBid() < price) {
					price = mdr.getHighestBid();
				}
				
				double amount = balance / (price);
				
				Thread.sleep(2 * 1000);
				
				buyOrderService.buyOrder(username, price, amount);
			}

		} catch (Exception e) {
			e.printStackTrace();
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
	
}
