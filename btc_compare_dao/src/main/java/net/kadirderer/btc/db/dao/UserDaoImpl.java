package net.kadirderer.btc.db.dao;

import net.kadirderer.btc.db.model.User;
import net.kadirderer.btc.db.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User findUser(String username) {
		return userRepository.findOne(username);
	}
	
	@Override
	public User findUser(String username, String password) {
		return userRepository.findUser(username, password);
	}

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public void updateLastLogin(String username) {
		userRepository.updateLastLogin(username);
	}

}
