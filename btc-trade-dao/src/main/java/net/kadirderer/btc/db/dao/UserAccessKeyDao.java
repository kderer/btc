package net.kadirderer.btc.db.dao;

import java.util.List;

import net.kadirderer.btc.db.model.UserAccessKey;

public interface UserAccessKeyDao {
	
	public List<UserAccessKey> findByUserName(String username);
	
	public UserAccessKey create(UserAccessKey uak);

}
