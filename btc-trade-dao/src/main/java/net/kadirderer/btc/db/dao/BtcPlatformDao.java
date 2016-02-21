package net.kadirderer.btc.db.dao;

import java.util.List;

import net.kadirderer.btc.db.model.BtcPlatform;

import org.springframework.transaction.annotation.Transactional;


public interface BtcPlatformDao {

	public BtcPlatform save(BtcPlatform platform);

	@Transactional(readOnly = true)
	public List<BtcPlatform> queryAll();

	public BtcPlatform queryByCode(String code);
	
	public BtcPlatform queryById(int id);
	
	public void delete(int id);

}
