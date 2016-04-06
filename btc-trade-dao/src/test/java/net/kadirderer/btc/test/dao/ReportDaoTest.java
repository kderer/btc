package net.kadirderer.btc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.kadirderer.btc.db.dao.ReportDao;
import net.kadirderer.btc.db.model.DailyReportDetail;
import net.kadirderer.btc.db.mybatis.persistence.ReportMapper;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class})
public class ReportDaoTest {
	
	@Autowired
	private ReportDao reportDao;
	
	@Autowired
	private ReportMapper reportMapper;
	
	@Test
	public void testDailyProfitReport() {
		List<DailyReportDetail> dailyProfitList = reportDao.queryDailyProfit();
		Assert.assertNotNull(dailyProfitList);
	}
	
	@Test
	public void testDailyProfitReport2() {
		List<net.kadirderer.btc.db.mybatis.domain.DailyProfitDetail> dailyProfitList = reportMapper.queryDailyProfit();
		Assert.assertNotNull(dailyProfitList);
	}

}
