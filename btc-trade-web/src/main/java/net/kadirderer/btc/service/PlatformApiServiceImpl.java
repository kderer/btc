package net.kadirderer.btc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.kadirderer.btc.db.dao.PlatformAPIDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.PlatformAPI;
import net.kadirderer.btc.util.enumaration.ApiType;
import net.kadirderer.btc.web.dto.PlatformApiDto;

@Service
public class PlatformApiServiceImpl extends BaseDtoService<PlatformAPI , PlatformApiDto> implements
		PlatformApiService {
	
	@Autowired
	private PlatformAPIDao paDao;
	
	@Override
	public PlatformAPI save(PlatformApiDto newPlatformApi) {
		return paDao.save(createModel(newPlatformApi));
	}

	@Override
	public List<PlatformApiDto> queryAll() {
		return createDtoList(paDao.queryAll());
	}

	@Override
	@Transactional
	public PlatformApiDto queryById(int id) {
		return createDto(paDao.queryById(id));
	}
	
	@Override
	public void deleteById(int id) {
		paDao.delete(id);
	}
	

	@Override
	public void customCopyToDto(PlatformAPI modelObject,
			PlatformApiDto dtoObject) {
		dtoObject.setType(modelObject.getType().name());
		dtoObject.setPlatformId(modelObject.getPlatform().getId());
	}

	@Override
	public void customCopyToModel(PlatformApiDto dtoObject,
			PlatformAPI modelObject) {
		modelObject.setType(ApiType.valueOf(dtoObject.getType()));
		
		BtcPlatform bp = new BtcPlatform();
		bp.setId(dtoObject.getPlatformId());
		
		modelObject.setPlatform(bp);
	}

}
