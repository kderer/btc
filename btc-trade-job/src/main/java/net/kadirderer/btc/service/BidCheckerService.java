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
import net.kadirderer.btc.impl.util.PriceAnalyzer;
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
		double dailLow = tickerResult.get24HoursLow();
				
		Statistics statistics = new Statistics();
		statistics.setGmoa(gmoa);
		statistics.setGmob(gmob);
		statistics.setHighestBid(highestBid);
		statistics.setLowestAsk(lowestAsk);
		statistics.setDailyHigh(dailyHigh);
		
		statisticsDao.save(statistics);
		
		if (Calendar.getInstance().getTimeInMillis() - lastRunTime >= cfgService.getBidCheckerBuyOrderCheckInterval() * 1000) {
			
			List<Statistics> latestStatistics = statisticsDao.findLatestNStatistics(cfgService.getBidCheckerBuyOrderCheckLastStatisticsCount());
			PriceAnalyzer pa = new PriceAnalyzer(latestStatistics, 33);
			
			if (!pa.isPriceIncreasing() || highestBid <= pa.getPreviosGmob()) {
				return;
			}
			
			double diff = pa.getLastGmob() - pa.getPreviosGmob();		
			
			if (diff < 1 ) {
				diff = 1.0; 
			}
			else {
				diff = Math.log(5.0 * diff);
			}
			
			diff = (highestBid - dailLow) / (10.0 * diff);
			double price = pa.getPreviosGmob() - diff;
			
			Double pendingAmount = userOrderDao.queryTotalPendingAutoUpdateOrderAmount(username, 9);
			
			if (pendingAmount == null ||
					cfgService.getAutoTradeTotalAmount() - pendingAmount >= cfgService.getAutoTradeBuyOrderAmount()) {				
				
				UserOrder order = new UserOrder();
				order.setUsername(username);
				order.setBasePrice(highestBid);
				order.setPrice(price);
				order.setAmount(cfgService.getAutoTradeBuyOrderAmount());
				order.setHighestGmob(gmob);
				order.addGmoa(gmoa, cfgService.getCheckLastGmobCountBuyOrder());
				order.addGmob(gmob, cfgService.getCheckLastGmobCountBuyOrder());
				order.setAutoUpdate(true);
				order.setAutoTrade(true);
				order.setStatus(OrderStatus.NEW.getCode());			
				
				buyOrderService.buyOrder(order);
			}
			
			pendingAmount = userOrderDao.queryTotalPendingNonUpdateOrderAmount(username, 9);
			
			if (pendingAmount == null ||
					cfgService.getNonAutoUpdateTotalAmount() - pendingAmount >= cfgService.getNonAutoUpdateBuyOrderAmount()) {
							
				UserOrder order = new UserOrder();
				order.setUsername(username);
				order.setBasePrice(highestBid);
				order.setPrice(price);
				order.setAmount(cfgService.getNonAutoUpdateBuyOrderAmount());
				order.setHighestGmob(gmob);
				order.setAutoUpdate(false);
				order.setAutoTrade(true);		
				
				buyOrderService.buyOrder(order);
			}
		}		
	}
}
