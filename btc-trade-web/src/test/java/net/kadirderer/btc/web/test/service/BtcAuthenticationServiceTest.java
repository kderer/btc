package net.kadirderer.btc.web.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.kadirderer.btc.service.BtcAuthenticationService;
import net.kadirderer.btc.web.dto.UserDto;
import net.kadirderer.btc.web.test.config.BaseTestConfig;

public class BtcAuthenticationServiceTest extends BaseTestConfig {

	@Autowired
	private BtcAuthenticationService authService;
	
	@Test
	public void testAuthentication() {
		UserDto user = authService.findUser("osman", "12qwzxas");
		
		Assert.assertNotNull(user);
		Assert.assertNotEquals(0, user.getRoleSet().size());
	}
	
	@Test
	public void testSignup() {
		authService.signupUser("kadir", "12qwzxas");
	}

}
