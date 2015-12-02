package net.kadirderer.btc.test.service;

import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.orderbook.BtcChinaOrderbookResult;
import net.kadirderer.btc.api.orderbook.OrderbookService;
import net.kadirderer.btc.config.PlatformApiConfig;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class, PlatformApiConfig.class})
public class BtcChinaBuyOrderTest {
	
	@Autowired
	private BuyOrderService btcChinaBuyOrderService;
	
	@Autowired
	private OrderbookService obService;
	
	@Autowired
	private BtcPlatformDao btcPlatformDao;
	
	
	@SuppressWarnings("unused")
	@Test
	public void testBuyOrder() {
		try {
			BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");		
			
			BtcChinaOrderbookResult qobResult = (BtcChinaOrderbookResult)obService.query(btcChina.getId());
			
			double highestBid = qobResult.getBids()[0][0];
			
			btcChinaBuyOrderService.buyOrder("kadir", 1956.5, 0.6475);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
