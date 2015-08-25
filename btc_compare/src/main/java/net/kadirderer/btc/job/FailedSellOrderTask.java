package net.kadirderer.btc.job;

import java.util.Calendar;
import java.util.Date;

import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.config.ConfigMap;
import net.kadirderer.btc.db.dao.FailedSellOrderDao;
import net.kadirderer.btc.db.model.FailedSellOrder;

import org.springframework.beans.factory.annotation.Autowired;

public class FailedSellOrderTask {
	
	@Autowired
	private FailedSellOrderDao failedSellOrderDao;
	
	@Autowired
	private SellOrderService sellOrderService;
	
	@Autowired
	private MarketDepthService marketDepthService;
	
	public void autoTradeFailedSellOrder() {
		try {
			if (ConfigMap.isAutoTradeEnabled()) {
				Date now = Calendar.getInstance().getTime();
				
				FailedSellOrder failedSellOrder = failedSellOrderDao.findFirstPending(now);
				
				while(failedSellOrder != null) {
					failedSellOrder.setStatus('D');
					failedSellOrderDao.save(failedSellOrder);
					
					double lowestAsk = marketDepthService.getMarketDepth("kadir").getLowestAsk();
					
					double price = failedSellOrder.getPrice();
					
					if(price < lowestAsk) {
						price = lowestAsk;
					}
					
					sellOrderService.sellOrder(failedSellOrder.getUsername(), 
							price, failedSellOrder.getAmount(),
							failedSellOrder.getBasePrice());
					
					failedSellOrder = failedSellOrderDao.findFirstPending(now);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
