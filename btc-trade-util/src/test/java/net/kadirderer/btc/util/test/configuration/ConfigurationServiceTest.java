package net.kadirderer.btc.util.test.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import net.kadirderer.btc.util.configuration.ConfigurationService;
import net.kadirderer.btc.util.test.config.BaseTestConfig;
import net.kadirderer.btc.util.test.config.DatabaseTestConfig;

@ContextConfiguration(classes = {DatabaseTestConfig.class})
public class ConfigurationServiceTest extends BaseTestConfig {
	
	@Autowired
	private ConfigurationService cfgService;
	
	@Test
	public void configurationQueryTest() {
		Assert.assertTrue(cfgService.isAutoTradeEnabled());
	}
	

}
