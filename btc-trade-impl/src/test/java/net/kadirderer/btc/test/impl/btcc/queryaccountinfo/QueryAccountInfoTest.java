package net.kadirderer.btc.test.impl.btcc.queryaccountinfo;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoResult;
import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoService;
import net.kadirderer.btc.test.config.BaseTestConfig;

public class QueryAccountInfoTest extends BaseTestConfig {
	
	@Autowired
	private QueryAccountInfoService queryAccountInfoService;
	
	@Test
	public void testQueryAccountInfo() throws Exception {
		
		QueryAccountInfoResult result = queryAccountInfoService.queryAccountInfo("kadir");
		
		Assert.assertNotNull(result.getCurrencyBalance());
		
	}

}
