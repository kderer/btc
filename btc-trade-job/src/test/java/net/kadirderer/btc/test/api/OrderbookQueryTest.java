package net.kadirderer.btc.test.api;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.kadirderer.btc.api.orderbook.OrderbookResult;
import net.kadirderer.btc.api.orderbook.OrderbookService;
import net.kadirderer.btc.test.config.BaseTestConfig;

public class OrderbookQueryTest extends BaseTestConfig {
	
	@Autowired
	private OrderbookService orderbookService;
	
	@Test
	public void testQueryOrderbook() throws Exception {
		
		OrderbookResult result = orderbookService.query(10);
		
		assertNotNull(result);
		
	}
	
	

}
