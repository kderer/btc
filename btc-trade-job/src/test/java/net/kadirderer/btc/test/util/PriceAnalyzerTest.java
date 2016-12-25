package net.kadirderer.btc.test.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.kadirderer.btc.db.dao.StatisticsDao;
import net.kadirderer.btc.db.model.Statistics;
import net.kadirderer.btc.impl.util.PriceAnalyzer;
import net.kadirderer.btc.test.config.BaseTestConfig;

public class PriceAnalyzerTest extends BaseTestConfig {
	
	@Autowired
	private StatisticsDao statisticsDao;
	
	@Test
	public void testPriceAnalyzer() {
		List<Statistics> statisticsList = statisticsDao.findLatestNStatistics(30);
		
		PriceAnalyzer pa = new PriceAnalyzer(statisticsList, 33);
		
		double lastGmob = pa.getLastGmob();
		double previousGmob = pa.getPreviosGmob();
		
		Assert.assertEquals(lastGmob > previousGmob, pa.isPriceIncreasing());
				
	}

}
