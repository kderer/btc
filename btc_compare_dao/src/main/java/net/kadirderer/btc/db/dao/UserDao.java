package net.kadirderer.btc.db.dao;

import net.kadirderer.btc.db.model.User;

public interface UserDao {
	
	public User findUser(String username);
	public User findUser(String username, String password);
	public User createUser(User user);
	public void updateLastLogin(String username);	
}
