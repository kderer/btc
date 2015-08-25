package net.kadirderer.btc.test.service;

import net.kadirderer.btc.config.ApplicationConfig;
import net.kadirderer.btc.config.PlatformApiConfig;
import net.kadirderer.btc.service.AutoTradeThreadService;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class, PlatformApiConfig.class, ApplicationConfig.class})
public class BtcChinaAutoTradeTest {
	
	@Autowired
	private AutoTradeThreadService btcChinaAutoTradeService;
	
	@Test
	public void testAutoTrade() throws Exception {
		btcChinaAutoTradeService.autoTrade("kadir");
	}

}
