package net.kadirderer.btc.service;

import net.kadirderer.btc.db.dao.UserDao;
import net.kadirderer.btc.db.dao.UserRoleDao;
import net.kadirderer.btc.db.model.User;
import net.kadirderer.btc.db.model.UserRole;
import net.kadirderer.btc.util.HashingUtil;
import net.kadirderer.btc.web.dto.UserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class BtcAuthenticationServiceImpl extends BaseDtoService<User, UserDto> implements BtcAuthenticationService, UserDetailsService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Override
	public UserDto findUser(String username, String password) {		
		return createDto(userDao.findUser(username, password));
	}
	
	@Override
	public boolean isUserAuthenticated(String username, String password) {
		User user = userDao.findUser(username, password);
		if(user != null) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException {

		User user = userDao.findUser(arg0);
		
		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		String[] authorities = new String[user.getRoleSet().size()];
		
		int i = 0;
		for(UserRole role : user.getRoleSet()) {
			authorities[i] = role.getRole();
			i += 1;
		}
		
		return new org.springframework.security.core.userdetails.User(
				arg0, user.getPassword(), AuthorityUtils.createAuthorityList(authorities));
	}
	
	
	@Override
	@Transactional
	public void updateLastLoginTime(String username) {
		userDao.updateLastLogin(username);
	}

	@Override
	public void customCopyToDto(User modelObject, UserDto dtoObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void customCopyToModel(UserDto dtoObject, User modelObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void signupUser(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(HashingUtil.generateBCryptedHash(password));		
		userDao.createUser(user);
		
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole("ADMIN");
		
		userRoleDao.create(userRole);
	}
	
}
