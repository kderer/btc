package net.kadirderer.btc.test.api;

import static org.junit.Assert.assertNotNull;
import net.kadirderer.btc.api.orderbook.OrderbookResult;
import net.kadirderer.btc.api.orderbook.OrderbookService;
import net.kadirderer.btc.test.config.TestConfig;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderbookQueryTest extends TestConfig {
	
	private OrderbookService orderbookService;
		
	@Override
	public void getObjectFromContext(AnnotationConfigApplicationContext context) {
		orderbookService = context.getBean(OrderbookService.class);
	}
	
	@Test
	public void testQueryOrderbook() throws Exception {
		
		OrderbookResult result = orderbookService.query(10);
		
		assertNotNull(result);
		
	}
	
	

}
