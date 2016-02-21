package net.kadirderer.btc.service;

import java.util.List;

import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.web.dto.BtcPlatformDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BtcPlatformServiceImpl extends BaseDtoService<BtcPlatform, BtcPlatformDto> implements BtcPlatformService  {

	@Autowired
	private BtcPlatformDao btcPlatformDao;
	
	@Override
	public BtcPlatform save(BtcPlatformDto newPlatform) {		
		BtcPlatform platform = createModel(newPlatform);
		btcPlatformDao.save(platform);		
		
		return platform;
	}

	@Override
	public List<BtcPlatformDto> queryAll() {
		return createDtoList(btcPlatformDao.queryAll());
	}
	

	@Override
	public BtcPlatformDto queryById(int id) {
		return createDto(btcPlatformDao.queryById(id));
	}

	@Override
	public void customCopyToDto(BtcPlatform modelObject,
			BtcPlatformDto dtoObject) {
		dtoObject.setUrl(modelObject.getHomeUrl());		
	}

	@Override
	public void customCopyToModel(BtcPlatformDto dtoObject,
			BtcPlatform modelObject) {
		modelObject.setHomeUrl(dtoObject.getUrl());	
	}

	@Override
	public void deleteById(int id) {
		btcPlatformDao.delete(id);
	}
	
}
