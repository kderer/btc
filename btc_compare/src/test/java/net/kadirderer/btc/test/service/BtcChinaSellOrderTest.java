package net.kadirderer.btc.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.kadirderer.btc.api.sellorder.SellOrderResult;
import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.config.ApplicationConfig;
import net.kadirderer.btc.config.PlatformApiConfig;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class, PlatformApiConfig.class, ApplicationConfig.class})
public class BtcChinaSellOrderTest {
	
	@Autowired
	private SellOrderService soService;
	
	@Test
	public void testSellOrder() throws Exception {
			
		SellOrderResult result = soService.sellOrder("kadir", 100000, 100000, 0);
		
		Assert.assertNotNull(result);
		
	}
	

}
