package net.kadirderer.btc.test.service;

import net.kadirderer.btc.api.cancelorder.CancelOrderService;
import net.kadirderer.btc.config.PlatformApiConfig;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class, PlatformApiConfig.class})
public class BtcChinaCancelOrderTest {
	
	@Autowired
	private CancelOrderService btcChinaCancelOrderService;
	
	@Autowired
	private UserOrderDao uoDao;
	
	@Test
	public void testCancelOrder() {
		try {
			
			btcChinaCancelOrderService.cancelOrder("kadir", "101647795");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
