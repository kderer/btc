package net.kadirderer.btc.test.dao;

import net.kadirderer.btc.api.ApiAccessKeyType;
import net.kadirderer.btc.db.dao.UserAccessKeyDao;
import net.kadirderer.btc.db.model.UserAccessKey;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class})
public class UserAccessKeyDaoTest {
	
	@Autowired
	private UserAccessKeyDao uakDao;
	
	@Test
	public void createTest() {
		UserAccessKey uak = new UserAccessKey();
		
		uak.setKeyType(ApiAccessKeyType.BTC_CHINA_ACCESS_KEY.getCode());
		uak.setKeyValue("asdasd");
		uak.setUsername("kadir");
		
		uakDao.create(uak);
		
		Assert.assertNotNull(uak.getId());		
	}
	
	@Test
	public void findTest() {
		Assert.assertEquals(0, uakDao.findByUserName("osman").size());
		
		Assert.assertEquals(1, uakDao.findByUserName("kadir").size());
	}
}
