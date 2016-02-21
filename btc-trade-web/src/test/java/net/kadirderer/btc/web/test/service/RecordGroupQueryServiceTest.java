package net.kadirderer.btc.web.test.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.kadirderer.btc.service.RecordGroupQueryService;
import net.kadirderer.btc.web.dto.RecordGroupLineChartDataResponse;
import net.kadirderer.btc.web.test.config.TestConfig;

import static org.junit.Assert.*;

public class RecordGroupQueryServiceTest extends TestConfig {

	private RecordGroupQueryService recordGroupQueryService;
	
	@Override
	public void getObjectFromContext(AnnotationConfigApplicationContext context) {
		recordGroupQueryService = context.getBean(RecordGroupQueryService.class);		
	}
	
	
	@Test
	public void testRecorGroupQuery() {
		
		Timestamp now = Timestamp.valueOf(LocalDateTime.now());
		Timestamp tenMinutesAgo = Timestamp.valueOf(LocalDateTime.now().minusMinutes(10));		
		
		RecordGroupLineChartDataResponse response = recordGroupQueryService.
				queryAllPlatformsBetweenTimes(tenMinutesAgo.toString(), now.toString(), 30);
		
		assertNotNull(response.getPlatformAskpricelistMap().get("BTCTURK"));
	}

}
