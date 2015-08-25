package net.kadirderer.btc.db.dao;

import java.util.List;

import net.kadirderer.btc.db.model.UserRole;

public interface UserRoleDao {
	
	public List<UserRole> findByUsername(String username);
	public UserRole create(UserRole userRole);

}
