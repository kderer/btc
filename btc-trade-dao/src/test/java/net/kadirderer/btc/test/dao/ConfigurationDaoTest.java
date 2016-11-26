package net.kadirderer.btc.test.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.kadirderer.btc.db.dao.ConfigurationDao;
import net.kadirderer.btc.db.model.Configuration;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class})
public class ConfigurationDaoTest {
	
	@Autowired
	private ConfigurationDao confDao;
	
	@Test
	public void saveTest() {
		Configuration conf = new Configuration();
		
		conf.setName("test");
		conf.setValue("yaraaqq");
		conf.setDescription("am got meme");
		
		confDao.save(conf);
		
		Assert.assertNotNull(conf.getId());		
	}
	
	@Test
	public void testQuery() {
		Page<Configuration> result = confDao.query(10, 0);
		
		Assert.assertNotNull(result.getContent());
		Assert.assertNotEquals(0, result.getTotalElements());		
	}
	
	@Test
	public void testDelete() {
		Configuration cfg = confDao.findByName("test");
		
		confDao.delete(cfg.getId());
	}
	

}
