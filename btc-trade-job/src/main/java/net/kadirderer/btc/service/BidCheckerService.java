package net.kadirderer.btc.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.marketdepth.MarketDepthResult;
import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.api.ticker.TickerResult;
import net.kadirderer.btc.api.ticker.TickerService;
import net.kadirderer.btc.db.dao.StatisticsDao;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.db.model.Statistics;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.util.configuration.ConfigurationService;
import net.kadirderer.btc.util.enumaration.OrderStatus;

@Service
public class BidCheckerService {
	
	@Autowired
	private MarketDepthService marketDepthService;
	
	@Autowired
	private BuyOrderService buyOrderService;
	
	@Autowired
	private UserOrderDao userOrderDao;
	
	@Autowired
	private ConfigurationService cfgService;
	
	@Autowired
	private StatisticsDao statisticsDao;
	
	@Autowired
	private TickerService tickerService;
	
	private Double highestGMOB;
	private Double lastHighestGMOB;
	private long lastRunTime;
		
	public synchronized void checkGMOB(String username) throws Exception {
		MarketDepthResult result = marketDepthService.getMarketDepth(username);
		TickerResult tickerResult = tickerService.getTicker(9);
		
		double[] maxAndGeometricMeanArray = result.getMaxAndGeometricMean();
		double lowestAsk = maxAndGeometricMeanArray[0];
		double gmoa = maxAndGeometricMeanArray[1];
		double highestBid = maxAndGeometricMeanArray[2];
		double gmob = maxAndGeometricMeanArray[3];
		
		double dailyHigh = tickerResult.get24HoursHigh();
		
		if (highestGMOB == null || highestGMOB < gmob) {
			highestGMOB = gmob;
		}
		
		if (lastHighestGMOB == null) {
			lastHighestGMOB = highestGMOB;
		}
		
		double price = highestBid + ((lowestAsk - highestBid) / 2.0);
		double checkDelta = cfgService.getBuyOrderHighestGmobLastGmobDelta();		
		
		if (lastHighestGMOB < highestGMOB) {
			checkDelta += (highestGMOB - lastHighestGMOB) / 4.0;
		}
		
		Statistics statistics = new Statistics();
		statistics.setGmoa(gmoa);
		statistics.setGmob(gmob);
		statistics.setHighestBid(highestBid);
		statistics.setHighestGmob(highestGMOB);
		statistics.setLastHighestGmob(lastHighestGMOB);
		statistics.setLowestAsk(lowestAsk);
		statistics.setCheckDelta(checkDelta);
		statistics.setHighestGmobPriceDiff(highestGMOB - price);
		statistics.setHighestLastGmobDiff(highestGMOB - lastHighestGMOB);
		
		if (highestBid < dailyHigh - cfgService.getAutoUpdateRange() && highestGMOB - price > checkDelta) {
			Double pendingAmount = userOrderDao.queryTotalPendingAutoUpdateOrderAmount(username, 9);
			
			if (pendingAmount == null ||
					cfgService.getAutoTradeTotalAmount() - pendingAmount >= cfgService.getAutoTradeBuyOrderAmount()) {
				UserOrder order = new UserOrder();
				order.setUsername(username);
				order.setBasePrice(highestBid);
				order.setPrice(highestBid - cfgService.getBuyOrderDelta());
				order.setAmount(cfgService.getAutoTradeBuyOrderAmount());
				order.setHighestGmob(highestGMOB);
				order.addGmoa(gmoa, cfgService.getCheckLastGmobCountBuyOrder());
				order.addGmob(gmob, cfgService.getCheckLastGmobCountBuyOrder());
				order.setAutoUpdate(true);
				order.setAutoTrade(true);
				order.setStatus(OrderStatus.NEW.getCode());			
				
				buyOrderService.buyOrder(order);
				
				lastHighestGMOB = highestGMOB;
				highestGMOB = gmob;
			}
		}
		
		statisticsDao.save(statistics);
		
		if (Calendar.getInstance().getTimeInMillis() - lastRunTime >= cfgService.getNonAutoUpdateOrderCheckInterval() * 1000 &&
				highestBid < dailyHigh - cfgService.getAutoUpdateRange()) {
			lastRunTime = Calendar.getInstance().getTimeInMillis();
			checkOnlyAutoTradeOrder(username, highestBid, gmob);			
		}		
	}
	
	private void checkOnlyAutoTradeOrder(String username, double highestBid, double gmob) throws Exception {
		Double pendingAmount = userOrderDao.queryTotalPendingNonUpdateOrderAmount(username, 9);
		
		if (pendingAmount != null &&
				cfgService.getNonAutoUpdateTotalAmount() - pendingAmount < cfgService.getNonAutoUpdateBuyOrderAmount()) {
			return;
		}
		
		List<Statistics> latestStatistics = statisticsDao.findLatestNStatistics(cfgService.getBidCheckerBuyOrderCheckLastStatisticsCount());
		Double price = null;
		if (latestStatistics != null && latestStatistics.size() > 0) {
			double product = 1.0;
			double count = 0;
			for (Statistics statistics : latestStatistics) {
				product *= statistics.getGmob();
				count += 1;
			}
			double gmogmob = Math.pow(product, 1.0 / count);
			
			if (gmob > gmogmob) {
				price = gmogmob;
			}
			else {
				return;
			}
		}		
		
		UserOrder order = new UserOrder();
		order.setUsername(username);
		order.setBasePrice(highestBid);
		order.setPrice(price);
		order.setAmount(cfgService.getNonAutoUpdateBuyOrderAmount());
		order.setHighestGmob(highestGMOB);
		order.setAutoUpdate(false);
		order.setAutoTrade(true);		
		
		buyOrderService.buyOrder(order);		
	}
}
