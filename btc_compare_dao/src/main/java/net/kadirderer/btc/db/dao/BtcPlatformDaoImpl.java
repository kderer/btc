package net.kadirderer.btc.db.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.repository.BtcPlatformRepository;

@Service
public class BtcPlatformDaoImpl implements BtcPlatformDao {
	
	@Autowired
	private BtcPlatformRepository btcPlatformRepository;

	@Override
	public BtcPlatform save(BtcPlatform platform) {
		return btcPlatformRepository.save(platform);
	}

	@Override
	public List<BtcPlatform> queryAll() {
		return btcPlatformRepository.findAll();
	}

	@Override
	public BtcPlatform queryByCode(String code) {
		return btcPlatformRepository.queryByCode(code);
	}

	@Override
	public BtcPlatform queryById(int id) {
		return btcPlatformRepository.findOne(id);
	}

	@Override
	public void delete(int id) {
		btcPlatformRepository.delete(id);;
	}

}
