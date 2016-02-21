package net.kadirderer.btc.test.dao;

import java.util.Calendar;

import net.kadirderer.btc.db.dao.UserDao;
import net.kadirderer.btc.db.model.User;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class})
public class UserDaoTest {
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void testFindByUsername() {
		User user = userDao.findUser("kadir");
		
		Assert.assertNotNull(user);		
	}
	
	
	@Test
	public void testFindByUsernamePAssword() {
		User user = userDao.findUser("kadir", "123456");
		
		Assert.assertNotNull(user);		
	}	
	
	@Test
	public void testUpdateLastLoginTime() {
		
		long now = Calendar.getInstance().getTimeInMillis();
		userDao.updateLastLogin("kadir");
		
		User user = userDao.findUser("kadir");
		long queried = user.getLastLoginTime().getTime();
		
		Assert.assertEquals(now, queried, 20);
	}

}
