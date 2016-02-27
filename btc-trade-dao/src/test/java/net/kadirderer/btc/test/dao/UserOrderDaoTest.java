package net.kadirderer.btc.test.dao;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.kadirderer.btc.db.criteria.UserOrderCriteria;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.test.config.DatabaseTestConfig;
import net.kadirderer.btc.util.enumaration.OrderStatus;
import net.kadirderer.btc.util.enumaration.OrderType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class})
public class UserOrderDaoTest {
	
	@Autowired
	private UserOrderDao uoDao;
	
	@Test
	public void saveTest() {
		UserOrder uo = new UserOrder();
		
		uo.setAmount(0.01);
		uo.setCreateDate(Calendar.getInstance().getTime());
		uo.setOrderType(OrderType.BUY.getCode());
		uo.setPlatformId(9);
		uo.setPrice(500.67);
		uo.setReturnId("12655");
		uo.setStatus(OrderStatus.PENDING.getCode());
		uo.setUsername("kadir");
		
		uoDao.save(uo);
		
		Assert.assertNotNull(uo.getId());		
	}
	
	
	@Test
	public void findLastPendingTest() {
		UserOrder uo = uoDao.findLastPending("kadir", 9, OrderType.BUY.getCode());
		
		Assert.assertEquals("12655", uo.getReturnId());
	}
	
	@Test
	public void testFindByCriteria() {
		UserOrderCriteria criteria = new UserOrderCriteria();
		criteria.addPlatformId(9);
		criteria.setProfitStart(0.0);
		
		List<UserOrder> resultList = uoDao.findByCriteria(criteria);
		
		Assert.assertNotEquals(0, resultList.size());
	}
	
	
	@Test
	public void testFindByCriteriaCount() {
		UserOrderCriteria criteria = new UserOrderCriteria();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -4);
		criteria.setCreateDateEnd(calendar.getTime());
		
		System.out.println(uoDao.findByCriteriaCount(criteria));
	}

}
