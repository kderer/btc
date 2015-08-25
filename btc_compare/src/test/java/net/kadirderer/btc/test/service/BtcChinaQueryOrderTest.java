package net.kadirderer.btc.test.service;

import net.kadirderer.btc.api.queryorder.BtcChinaQueryOrderResult;
import net.kadirderer.btc.api.queryorder.QueryOrderService;
import net.kadirderer.btc.config.PlatformApiConfig;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class, PlatformApiConfig.class})
public class BtcChinaQueryOrderTest {
	
	@Autowired
	private QueryOrderService qoService;
	
	@Test
	public void testQueryOrder() {
		
		try {
//			BtcChinaQueryOrderResult result = (BtcChinaQueryOrderResult) qoService.queryOrder("kadir", "2");
//			
//			Assert.assertNotNull(result.getError());
			
			BtcChinaQueryOrderResult result = (BtcChinaQueryOrderResult) qoService.queryOrder("kadir", "102724718");
			
			Assert.assertEquals(10, result.getPrice(), 0);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
