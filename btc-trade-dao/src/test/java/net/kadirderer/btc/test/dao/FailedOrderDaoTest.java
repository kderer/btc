package net.kadirderer.btc.test.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.kadirderer.btc.db.dao.FailedOrderDao;
import net.kadirderer.btc.db.model.FailedOrder;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class})
public class FailedOrderDaoTest {
	
	@Autowired
	private FailedOrderDao failedOrderDao;
	
	@Test
	public void testSave() {
		FailedOrder failedOrder = new FailedOrder();
		
		Assert.assertNotNull(failedOrderDao.save(failedOrder).getId());	
	}
	
}
