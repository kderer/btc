package net.kadirderer.btc.test.service;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.config.ApplicationConfig;
import net.kadirderer.btc.config.ConfigMap;
import net.kadirderer.btc.config.PlatformApiConfig;
import net.kadirderer.btc.db.dao.FailedSellOrderDao;
import net.kadirderer.btc.db.model.FailedSellOrder;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class, PlatformApiConfig.class, ApplicationConfig.class})
public class FailedSellOrderTaskTest {
	
	@Autowired
	private FailedSellOrderDao failedSellOrderDao;
	
	@Autowired
	private SellOrderService sellOrderService;
	
	@Test
	public void testAutoTradeFailedSellOrder() {
		
		try {
			if (ConfigMap.isAutoTradeEnabled()) {
				Date now = Calendar.getInstance().getTime();
				
				FailedSellOrder failedSellOrder = failedSellOrderDao.findFirstPending(now);
				
				while(failedSellOrder != null) {
					failedSellOrder.setStatus('D');
					failedSellOrderDao.save(failedSellOrder);
					
					sellOrderService.sellOrder(failedSellOrder.getUsername(), 
							failedSellOrder.getPrice(), failedSellOrder.getAmount(),
							failedSellOrder.getBasePrice());
					
					failedSellOrder = failedSellOrderDao.findFirstPending(now);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
