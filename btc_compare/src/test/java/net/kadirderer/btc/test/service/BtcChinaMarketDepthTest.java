package net.kadirderer.btc.test.service;

import net.kadirderer.btc.api.marketdepth.BtcChinaMarketDepthResult;
import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.config.ApplicationConfig;
import net.kadirderer.btc.config.PlatformApiConfig;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class, PlatformApiConfig.class, ApplicationConfig.class})
public class BtcChinaMarketDepthTest {
	
	@Autowired
	private MarketDepthService mds;
	
	@Test
	public void testMarketDepth() throws Exception {
		BtcChinaMarketDepthResult result = (BtcChinaMarketDepthResult)mds.getMarketDepth("kadir");
		
		Assert.assertNotEquals(0, result.getPriceOfMaxAskAmount());
		System.out.println(result.getPriceOfMaxAskAmount());
		Assert.assertNotEquals(0, result.getPriceOfMaxBidAmount());
		System.out.println(result.getPriceOfMaxBidAmount());
		
		System.out.println("Highest bid: " + result.getHighestBid());
		System.out.println("Lowest ask: " + result.getLowestAsk());
		
	}

}
