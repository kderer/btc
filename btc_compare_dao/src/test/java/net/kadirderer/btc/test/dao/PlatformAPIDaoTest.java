package net.kadirderer.btc.test.dao;

import static org.junit.Assert.assertNotNull;
import net.kadirderer.btc.api.ApiType;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.dao.PlatformAPIDao;
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
public class PlatformAPIDaoTest {

	@Autowired
	private PlatformAPIDao platformApiDao;
	
	@Autowired
	private BtcPlatformDao btcPlatformDao;
	
	@Test
	public void testInsert() {
		
		BtcPlatform bp = btcPlatformDao.queryByCode("BTCCHINA");
		
		PlatformAPI platformAPI = new PlatformAPI();		
		platformAPI.setType(ApiType.SELLORDER);
		platformAPI.setUrl("https://api.btcchina.com/api_trade_v1.php");
		platformAPI.setReturnType("net.kadirderer.btc.api.sellOrder.BtcChinaSellOrderResult");
		platformAPI.setPlatform(bp);
		
		platformApiDao.save(platformAPI);
		
		assertNotNull(platformAPI.getId());		
	}
	
	@Test
	public void testFindByApiType() {
		assertNotNull(platformApiDao.findByApiType("BTCCHINA", ApiType.BUYORDER));
	}
	
	

}
