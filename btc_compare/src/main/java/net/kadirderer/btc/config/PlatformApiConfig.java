package net.kadirderer.btc.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.kadirderer.btc.api.ApiType;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.PlatformAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"net.kadirderer.btc.api"})
public class PlatformApiConfig {
	
	@Autowired
	private BtcPlatformDao btcPlatformDao;
	
	@Bean
	public Map<Integer, Map<ApiType, PlatformAPI>> platformApiMap() {		
		
		List<BtcPlatform> platformList = btcPlatformDao.queryAll();
		
		Map<Integer, Map<ApiType, PlatformAPI>> platformMap = new HashMap<Integer, Map<ApiType, PlatformAPI>>();
		
		for(BtcPlatform btcPlatform : platformList) {
			
			Map<ApiType, PlatformAPI> platformApiMap = new HashMap<ApiType, PlatformAPI>();
			
			List<PlatformAPI> platformApiList = btcPlatform.getApiList();
			
			for(PlatformAPI platformAPI : platformApiList) {
				platformApiMap.put(platformAPI.getType(), platformAPI);
			}
			
			platformMap.put(btcPlatform.getId(), platformApiMap);
		}
				
		return platformMap;
	}	
}
