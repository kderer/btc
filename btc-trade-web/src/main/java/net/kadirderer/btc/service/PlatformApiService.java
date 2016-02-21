package net.kadirderer.btc.service;

import java.util.List;

import net.kadirderer.btc.db.model.PlatformAPI;
import net.kadirderer.btc.web.dto.PlatformApiDto;

public interface PlatformApiService {
	
	public PlatformAPI save(PlatformApiDto newPlatform);
	
	public List<PlatformApiDto> queryAll();
	
	public PlatformApiDto queryById(int id);
	
	public void deleteById(int id);

}
