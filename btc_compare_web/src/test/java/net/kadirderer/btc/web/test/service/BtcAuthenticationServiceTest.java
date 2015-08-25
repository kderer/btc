package net.kadirderer.btc.web.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.kadirderer.btc.service.BtcAuthenticationService;
import net.kadirderer.btc.web.dto.UserDto;
import net.kadirderer.btc.web.test.config.TestConfig;

public class BtcAuthenticationServiceTest extends TestConfig {

	private BtcAuthenticationService authService;
	
	@Override
	public void getObjectFromContext(AnnotationConfigApplicationContext context) {
		authService = context.getBean(BtcAuthenticationService.class);		
	}
	
	@Test
	public void testAuthentication() {
		UserDto user = authService.findUser("kadir", "123456");
		
		Assert.assertNotNull(user);
		Assert.assertNotEquals(0, user.getRoleSet().size());
	}

}
