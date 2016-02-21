package net.kadirderer.btc.test.impl.btcc;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.kadirderer.btc.api.orderbook.OrderbookService;
import net.kadirderer.btc.impl.orderbook.BtcChinaOrderbookResult;
import net.kadirderer.btc.test.config.BaseTestConfig;

public class OrderBookTest extends BaseTestConfig {
	
	@Autowired
	private OrderbookService orderbookService;
		
	@Test
	public void testOrderBook() throws Exception {
		BtcChinaOrderbookResult result = (BtcChinaOrderbookResult) orderbookService.query(9);
		
		Assert.assertNotNull(result);
	}
}
