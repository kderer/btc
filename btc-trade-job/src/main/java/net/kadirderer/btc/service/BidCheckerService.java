package net.kadirderer.btc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.marketdepth.MarketDepthResult;
import net.kadirderer.btc.api.marketdepth.MarketDepthService;
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
	
	private Double highestGMOB;
	private Double lastHighestGMOB;
		
	public synchronized void checkGMOB(String username) throws Exception {
		MarketDepthResult result = marketDepthService.getMarketDepth(username);
		
		double[] maxAndGeometricMeanArray = result.getMaxAndGeometricMean();
		double lowestAsk = maxAndGeometricMeanArray[0];
		double gmoa = maxAndGeometricMeanArray[1];
		double highestBid = maxAndGeometricMeanArray[2];
		double gmob = maxAndGeometricMeanArray[3];
		
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
		
		if (highestGMOB - price > checkDelta) {
			Double pendingAmount = userOrderDao.queryTotalPendingOrderAmount(username, 9);
			
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
	}
}
