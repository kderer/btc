package net.kadirderer.btc.db.dao;

import java.util.List;

import net.kadirderer.btc.api.ApiType;
import net.kadirderer.btc.db.model.PlatformAPI;

public interface PlatformAPIDao {
	
	public PlatformAPI save(PlatformAPI plarformApi);
	
	public List<PlatformAPI> queryAll();
	
	public PlatformAPI queryById(int id);
	
	public PlatformAPI findByApiType(String platformCode, ApiType apiType);
	
	public void delete(int id);
}
