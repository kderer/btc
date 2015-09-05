package net.kadirderer.btc.web.test.service;

import java.util.List;

import net.kadirderer.btc.service.ReportService;
import net.kadirderer.btc.web.dto.DailyReportDto;
import net.kadirderer.btc.web.test.config.TestConfig;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ReportServiceTest extends TestConfig {
	
	private ReportService reportService;

	@Override
	public void getObjectFromContext(AnnotationConfigApplicationContext context) {
		reportService = context.getBean(ReportService.class);
	}
	
	@Test
	public void testQueryDailyReport() {
		List<DailyReportDto> reportList = reportService.queryDailyReport();
		
		Assert.assertNotEquals(0, reportList.size());
	}

}
