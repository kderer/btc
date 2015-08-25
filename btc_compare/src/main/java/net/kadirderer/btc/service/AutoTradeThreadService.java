package net.kadirderer.btc.service;

import java.util.Calendar;
import java.util.List;

import net.kadirderer.btc.api.OrderType;
import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.cancelorder.CancelOrderService;
import net.kadirderer.btc.api.marketdepth.MarketDepthResult;
import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.api.orderbook.OrderbookService;
import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoResult;
import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoService;
import net.kadirderer.btc.api.queryorder.QueryOrderService;
import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.config.ConfigMap;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.db.model.UserOrder;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AutoTradeThreadService {
	
	@Autowired
	private UserOrderDao userOrderDao;
	
	public abstract SellOrderService getSellOrderService();
	
	public abstract BuyOrderService getBuyOrderService();
	
	public abstract CancelOrderService getCancelOrderService();
	
	public abstract QueryOrderService getQueryOrderService();
	
	public abstract OrderbookService getOrderBookService();
	
	public abstract MarketDepthService getMarketDepthService();
	
	public abstract QueryAccountInfoService getQueryAccountInfoService();
	
	public abstract double getBtcBalanceMinimumLimit();
	
	public abstract double getCurrencyMinimumLimit();
	
	public abstract int getPlatformId();
	
	public abstract double getDeltaForTrade();
	
	public abstract int getPeriod();
	
	private boolean isFirstTime = true;
	
	public void autoTrade(String username) throws Exception {		
			
		List<UserOrder> pendingOrders = userOrderDao.findPending(username, getPlatformId());
		
		if(pendingOrders != null && pendingOrders.size() > 0) {
			for(UserOrder order : pendingOrders) {				
				if(Calendar.getInstance().getTimeInMillis() - order.getCreateDate().getTime() >= 1000 * 60 * 10) {
					getCancelOrderService().cancelOrder(username, order.getReturnId());						
				} else if (isFirstTime && order.getOrderType() == OrderType.BUY.getCode()) {
					BuyOrderThread bot = new BuyOrderThread();
					bot.setCancelOrderService(getCancelOrderService());
					bot.setOrderId(order.getReturnId());
					bot.setQueryOrderService(getQueryOrderService());
					bot.setSellOrderService(getSellOrderService());
					bot.setBuyOrderService(getBuyOrderService());
					bot.setMarketDepthService(getMarketDepthService());
					bot.setUsername(username);
					
					Thread boThread = new Thread(bot);
					boThread.start();					
				} else if (isFirstTime && order.getOrderType() == OrderType.SELL.getCode()) {
					SellOrderThread sot = new SellOrderThread();
					sot.setCancelOrderService(getCancelOrderService());
					sot.setOrderId(order.getReturnId());
					sot.setQueryOrderService(getQueryOrderService());
					sot.setBuyOrderService(getBuyOrderService());
					sot.setSellOrderService(getSellOrderService());
					sot.setMarketDepthService(getMarketDepthService());
					sot.setUsername(username);
					
					Thread boThread = new Thread(sot);
					boThread.start();
				}
			}
		}
		
//		OrderbookResult obResult = getOrderBookService().query(getPlatformId());			
//		double lowestAsk = obResult.getHighestAsk();
//		double highestBid = obResult.getHighestBid();
		
		MarketDepthResult mdr = getMarketDepthService().getMarketDepth(username);
		double lowestAsk = mdr.getHighestBid() + ConfigMap.sellOrderDelta();
		double highestBid = mdr.getLowestAsk() - ConfigMap.buyOrderDelta();
		
		QueryAccountInfoResult qaiResult = getQueryAccountInfoService().queryAccountInfo(username);
		double btcBalance = qaiResult.getBtcBalance();
		double currencyBalance = qaiResult.getCurrencyBalance();
		
		if(currencyBalance > 0) {
			getBuyOrderService().buyOrder(username, highestBid, currencyBalance / highestBid);		
		}
		
		if(btcBalance > 0 && isFirstTime) {
			getSellOrderService().sellOrder(username, lowestAsk, btcBalance, 0);
		}
		
		isFirstTime = false;
	}

}
