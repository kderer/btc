package net.kadirderer.btc.job;

import org.springframework.beans.factory.annotation.Autowired;

import net.kadirderer.btc.config.ConfigMap;
import net.kadirderer.btc.service.AutoTradeService;

public class SweeperTask {
	
	@Autowired
	private AutoTradeService atService;	
	
	public void sweep() {
		try {
			if (ConfigMap.isAutoTradeEnabled()) {
				atService.sweep("kadir");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
