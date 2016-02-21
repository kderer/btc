package net.kadirderer.btc.service;

import net.kadirderer.btc.web.dto.UserDto;

public interface BtcAuthenticationService {
	
	public UserDto findUser(String username, String password);
	
	public boolean isUserAuthenticated(String username, String password);
	
	public void updateLastLoginTime(String username);
	
	public void signupUser(String username, String password);
	
}
