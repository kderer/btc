package net.kadirderer.btc.test.dao;

import java.util.Calendar;

import net.kadirderer.btc.db.dao.FailedSellOrderDao;
import net.kadirderer.btc.db.model.FailedSellOrder;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class})
public class FailedOrderDaoTest {
	
	@Autowired
	private FailedSellOrderDao failedOrderDao;
	
	@Test
	public void testSave() {
		FailedSellOrder failedOrder = new FailedSellOrder();
		
		failedOrder.setAmount(0.09);
		failedOrder.setMessage("Unexpected error occurred");
		failedOrder.setPlatformId(9);
		failedOrder.setPrice(1498.52);
		failedOrder.setStatus('P');
		failedOrder.setUsername("kadir");
		
		Assert.assertNotNull(failedOrderDao.save(failedOrder).getId());	
	}
	
	
	@Test
	public void testFindFirstPending() {
		Assert.assertEquals("P", failedOrderDao.findFirstPending(Calendar.getInstance().getTime()).getStatus() + "");
	}
}
