package net.kadirderer.btc.web.test.service;

import net.kadirderer.btc.service.ConfigService;
import net.kadirderer.btc.web.test.config.TestConfig;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigServiceTest extends TestConfig {
	
	private ConfigService configService;

	@Override
	public void getObjectFromContext(AnnotationConfigApplicationContext context) {
		configService = context.getBean(ConfigService.class);
	}
	
	@Test
	public void testSendConfigSetMessage() {
		configService.setConfig("test", "testvalue");
	}

}
