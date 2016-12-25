package net.kadirderer.btc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.kadirderer.btc.db.dao.StatisticsDao;
import net.kadirderer.btc.db.model.Statistics;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class})
public class StatisticsDaoTest {
	
	@Autowired
	private StatisticsDao statisticsDao;
	
	@Test
	public void testDailyProfitReport() {
		List<Statistics> latestStatistics = statisticsDao.findLatestNStatistics(45);
				
		double product = 1.0;
		double count = 0;
		for (Statistics statistics : latestStatistics) {
			product *= statistics.getGmob();
			count += 1;
		}
		double gmogmob = Math.pow(product, 1.0 / count);
		
		Assert.assertNotEquals(0, gmogmob);
	}
	
}
