package net.kadirderer.btc.test.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.PlatformAPI;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class})
public class BtcPlatformDaoTest {
	
	@Autowired
	private BtcPlatformDao btcPlatformDao;	
	
	@Test
	public void insertTest() {
		BtcPlatform platform = new BtcPlatform();
		
		platform.setId(12);
		platform.setCode("BTCCHINA test upd");
		platform.setName("BTC CHINA test");
		platform.setHomeUrl("https://www.btcchina.com/");
		platform.setCurrency("CNY");
		
		btcPlatformDao.save(platform);
		
		assertNotNull(platform.getId());
		
	}
	
	@Test
	public void testQueryByCode() {
		BtcPlatform result = btcPlatformDao.queryByCode("BTCTURK");
		
		List<PlatformAPI> apiList = result.getApiList();
		
		assertNotNull(apiList);
	}

}
