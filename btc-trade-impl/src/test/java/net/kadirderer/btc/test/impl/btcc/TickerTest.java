package net.kadirderer.btc.test.impl.btcc;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.kadirderer.btc.api.ticker.TickerService;
import net.kadirderer.btc.impl.ticker.BtcChinaTickerResult;
import net.kadirderer.btc.test.config.BaseTestConfig;

public class TickerTest extends BaseTestConfig {
	
	@Autowired
	private TickerService tickerService;
		
	@Test
	public void testTickerService() throws Exception {
		BtcChinaTickerResult result = (BtcChinaTickerResult) tickerService.getTicker(9);
		
		Assert.assertNotEquals(result.get24HoursHigh(), 0);
	}
}
