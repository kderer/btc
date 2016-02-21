package net.kadirderer.btc.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import net.kadirderer.btc.db.dao.RecordGroupDao;
import net.kadirderer.btc.db.model.RecordGroup;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class})
public class RecordGroupDaoTest {

	@Autowired
	private RecordGroupDao recordGroupDao;
	
	@Test
	public void testInsert() {
		RecordGroup group = new RecordGroup();
		
		recordGroupDao.insert(group);
		
		assertNotNull(group.getRecordGroupId());				
	}
	
	@Test
	public void testQueryBetweenTime() {
		
		LocalDateTime nowTime = LocalDateTime.now();
		LocalDateTime tenMinutesAgo = nowTime.minusMinutes(50000);
		
		Timestamp end = Timestamp.valueOf(nowTime);
		Timestamp start = Timestamp.valueOf(tenMinutesAgo);
		
		List<Integer> platformIdList = new ArrayList<Integer>();
		platformIdList.add(8);
		
		List<RecordGroup> resultList = recordGroupDao.queryBetweenTime(start, end, 1000, null);
		
		assertEquals(25, resultList.size(), 3);
		
	}
	
	@Test
	public void testQueryById() {
		RecordGroup group = recordGroupDao.queryById(12333);
		
		assertNotNull(group);
	}
	
	@Test
	public void testCount() {
		LocalDateTime nowTime = LocalDateTime.now(ZoneId.of("GMT+8"));
		LocalDateTime tenMinutesAgo = nowTime.minusMinutes(1000);
		
		Timestamp end = Timestamp.valueOf(nowTime);
		Timestamp start = Timestamp.valueOf(tenMinutesAgo);
		
		List<Integer> platformIdList = new ArrayList<Integer>();
		platformIdList.add(8);
		
		Integer result = recordGroupDao.getRecordGroupCountBetweenTime(start, end, platformIdList);
		
		assertEquals(10, result, 5);
	}
	
}
