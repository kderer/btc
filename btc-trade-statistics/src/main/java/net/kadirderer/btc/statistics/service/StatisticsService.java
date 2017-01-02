package net.kadirderer.btc.statistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.api.marketdepth.MarketDepthResult;
import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.api.ticker.TickerResult;
import net.kadirderer.btc.api.ticker.TickerService;
import net.kadirderer.btc.db.dao.StatisticsDao;
import net.kadirderer.btc.db.model.Statistics;

@Service
public class StatisticsService {
	
	@Autowired
	private MarketDepthService marketDepthService;
	
	@Autowired
	private StatisticsDao statisticsDao;
	
	@Autowired
	private TickerService tickerService;
		
	public synchronized void createNewStatistics(String username) throws Exception {
		MarketDepthResult result = marketDepthService.getMarketDepth(username);
		TickerResult tickerResult = tickerService.getTicker(9);
		
		double[] maxAndGeometricMeanArray = result.getMaxAndGeometricMean();
		double lowestAsk = maxAndGeometricMeanArray[0];
		double gmoa = maxAndGeometricMeanArray[1];
		double highestBid = maxAndGeometricMeanArray[2];
		double gmob = maxAndGeometricMeanArray[3];
		
		double dailyHigh = tickerResult.get24HoursHigh();
		double dailyLow = tickerResult.get24HoursLow();
				
		Statistics statistics = new Statistics();
		statistics.setGmoa(gmoa);
		statistics.setGmob(gmob);
		statistics.setHighestBid(highestBid);
		statistics.setLowestAsk(lowestAsk);
		statistics.setDailyHigh(dailyHigh);
		statistics.setDailyLow(dailyLow);
		
		statisticsDao.save(statistics);				
	}
}
