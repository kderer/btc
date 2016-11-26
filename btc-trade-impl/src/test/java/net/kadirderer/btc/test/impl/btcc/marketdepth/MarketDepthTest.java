package net.kadirderer.btc.test.impl.btcc.marketdepth;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.test.config.BaseTestConfig;

public class MarketDepthTest  extends BaseTestConfig {
	
	@Autowired
	private MarketDepthService marketDepthService;
	
	@Test
	public void geometricMeanOfBidTest() throws Exception {
		double highestBid = marketDepthService.getMarketDepth("kadir").getHighestBid();
		double geometricMean = marketDepthService.getMarketDepth("kadir").getGeometricMeanOfBids();
		
		System.out.println("Highest Bid: " + highestBid);
		System.out.println("Geometric Mean: " + geometricMean);
		
		boolean isGeometricMeanSmaller = highestBid > geometricMean;
		
		Assert.assertTrue(isGeometricMeanSmaller);
	}
	
	@Test
	public void getMaxAndGMTest() throws Exception {
		double[] magmArray = marketDepthService.getMarketDepth("kadir").getMaxAndGeometricMean();
		double highestBid = magmArray[2];
		double gmob = magmArray[3];
		double lowestAsk = magmArray[0];
		double gmoa = magmArray[1];		
		
		System.out.println("Lowest Ask: " + lowestAsk);
		System.out.println("GMOA: " + gmoa);
		System.out.println("Highest Bid: " + highestBid);
		System.out.println("GMOB: " + gmob);
		
		boolean isGeometricMeanSmaller = highestBid > gmob;
		
		Assert.assertTrue(isGeometricMeanSmaller);
	}
}
