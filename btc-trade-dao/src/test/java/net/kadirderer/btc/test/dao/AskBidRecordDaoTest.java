package net.kadirderer.btc.test.dao;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import net.kadirderer.btc.db.dao.AskBidRecordDao;
import net.kadirderer.btc.db.model.AskBidRecord;
import net.kadirderer.btc.db.model.RecordGroup;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class})
public class AskBidRecordDaoTest {
	
	@Autowired
	private AskBidRecordDao askBidRecordDao;
	
	@Test
	public void testObtainGroupId() {
		Integer id = askBidRecordDao.obtainRecordGroupId();		
		assertNotNull(id);
	}
	
	@Test
	public void testQueryAll() {
		List<AskBidRecord> list = askBidRecordDao.queryAllRecords();
		
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void insertTest() {
		
		AskBidRecord askbidRecord = new AskBidRecord();	
		
		askbidRecord.setAskRate(2.2);
		askbidRecord.setBidRate(2.6);
		askbidRecord.setPlatformId(1233);
		
		askBidRecordDao.insert(askbidRecord);
		
	}
	
	
	@Test
	public void insertWithRecordGroupTest() {
		
		RecordGroup group = new RecordGroup();
		group.setRecordGroupId(123123);
		
		AskBidRecord askbidRecord = new AskBidRecord();	
		
		askbidRecord.setAskRate(2.2);
		askbidRecord.setBidRate(2.6);
		askbidRecord.setPlatformId(1233);
		
		askBidRecordDao.insert(askbidRecord);
		
		assertNotNull(askbidRecord.getId());
		
	}
	
}
