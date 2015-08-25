package net.kadirderer.btc.test.service;

import java.util.ArrayList;
import java.util.List;

import net.kadirderer.btc.db.dao.RecordGroupDao;
import net.kadirderer.btc.db.model.RecordGroup;
import net.kadirderer.btc.service.RecordGroupService;
import net.kadirderer.btc.test.config.TestConfig;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RecordGroupServiceTest extends TestConfig {

	private RecordGroupService recordGroupService;
	private RecordGroupDao recordGroupDao;
	
	@Override
	public void getObjectFromContext(AnnotationConfigApplicationContext context) {
		recordGroupService = context.getBean(RecordGroupService.class);
		recordGroupDao = context.getBean(RecordGroupDao.class);
	}
	
	@Test
	public void testCreateRecordGroup() {
		
		List<Integer> platformList = new ArrayList<Integer>();
		platformList.add(8);
		platformList.add(9);
		
		try {
			RecordGroup group = recordGroupService.insertRecordGroup(platformList);
			
			group = recordGroupDao.queryById(group.getRecordGroupId());
			
			assertEquals(2, group.getAskBidRecords().size());
			assertEquals(450, group.getExchangeRates().getRatesMap().length(), 50);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	@Test
	public void testQueryFromAllPlatform() throws Exception {
		recordGroupService.createRecordForAllPlatforms();
	}
	
	

}
