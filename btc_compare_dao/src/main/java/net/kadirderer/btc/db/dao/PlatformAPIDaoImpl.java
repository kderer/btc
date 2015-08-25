package net.kadirderer.btc.db.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.api.ApiType;
import net.kadirderer.btc.db.model.PlatformAPI;
import net.kadirderer.btc.db.repository.PlatformAPIRespository;

@Service
public class PlatformAPIDaoImpl implements PlatformAPIDao {

	@Autowired
	private PlatformAPIRespository platformApiRepository;
	
	@Override
	public PlatformAPI save(PlatformAPI plarformApi) {
		return platformApiRepository.save(plarformApi);
	}

	@Override
	public List<PlatformAPI> queryAll() {
		return platformApiRepository.findAll();
	}

	@Override
	public PlatformAPI queryById(int id) {
		return platformApiRepository.findOne(id);
	}

	@Override
	public PlatformAPI findByApiType(String platformCode, ApiType apiType) {
		return platformApiRepository.findByApiType(platformCode, apiType);
	}

	@Override
	public void delete(int id) {
		platformApiRepository.delete(id);
	}

}
