package net.kadirderer.btc.db.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.db.model.UserAccessKey;
import net.kadirderer.btc.db.repository.UserAccessKeyRepository;

@Service
public class UserAccessKeyDaoImpl implements UserAccessKeyDao {

	@Autowired
	private UserAccessKeyRepository uakRepository;	
	
	@Override
	public List<UserAccessKey> findByUserName(String username) {
		return uakRepository.findByUsername(username);
	}

	@Override
	public UserAccessKey create(UserAccessKey uak) {
		return uakRepository.save(uak);
	}

}
