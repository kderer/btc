package net.kadirderer.btc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.model.BtcPlatform;

import org.springframework.beans.factory.annotation.Autowired;

public class CacheService {
	
	@Autowired
	private BtcPlatformDao btcPlatformDao;
	
	private Map<Integer, BtcPlatform> platformMap = new HashMap<Integer, BtcPlatform>();
	private List<BtcPlatform> platformList = new ArrayList<BtcPlatform>();
	
	public void init() {
		List<BtcPlatform> allPlatformList = btcPlatformDao.queryAll();
		
		for(BtcPlatform platform : allPlatformList) {
			platformMap.put(platform.getId(), platform);
			platformList.add(platform);
		}
	}
	
	public BtcPlatform getPlatform(Integer platfromId) {
		return platformMap.get(platfromId);
	}

	public List<BtcPlatform> getPlatformList() {
		return platformList;
	}

}
