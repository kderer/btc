package net.kadirderer.btc.service;

import java.util.List;

import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.web.dto.BtcPlatformDto;

public interface BtcPlatformService {
	
	public BtcPlatform save(BtcPlatformDto newPlatform);
	public List<BtcPlatformDto> queryAll();
	public BtcPlatformDto queryById(int id);
	public void deleteById(int id);

}
