package net.kadirderer.btc.test.service;

import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoService;
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
public class BtcChinaQueryAccountInfoServiceTest {
	
	@Autowired
	private QueryAccountInfoService qaiService;
	
	@Test
	public void testQueryAccountInfo() throws Exception {
		Assert.assertEquals(304, qaiService.queryAccountInfo("kadir").getCurrencyBalance(), 1);
	}

}
