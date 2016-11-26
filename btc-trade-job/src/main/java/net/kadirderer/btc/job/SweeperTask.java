package net.kadirderer.btc.job;

import org.springframework.beans.factory.annotation.Autowired;

import net.kadirderer.btc.service.AutoTradeService;
import net.kadirderer.btc.util.configuration.ConfigurationService;

public class SweeperTask {
	
	@Autowired
	private AutoTradeService atService;
	
	@Autowired
	private ConfigurationService cfgService;
	
	public void sweep() {
		try {
			if (cfgService.isAutoTradeEnabled()) {
				atService.sweep("kadir");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
