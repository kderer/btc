package net.kadirderer.btc.db.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.db.model.UserRole;
import net.kadirderer.btc.db.repository.UserRoleRepository;

@Service
public class UserRoleDaoImpl implements UserRoleDao {
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public List<UserRole> findByUsername(String username) {
		return userRoleRepository.findByUserName(username);
	}

	@Override
	public UserRole create(UserRole userRole) {
		return userRoleRepository.save(userRole);
	}

}
