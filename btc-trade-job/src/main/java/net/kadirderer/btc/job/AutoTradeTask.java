package net.kadirderer.btc.job;

import org.springframework.beans.factory.annotation.Autowired;

import net.kadirderer.btc.service.AutoTradeService;
import net.kadirderer.btc.util.configuration.ConfigurationService;

public class AutoTradeTask {
	
	@Autowired
	private AutoTradeService atService;
	
	@Autowired
	private ConfigurationService cfgService;	
	
	public void autoTrade() {
		try {
			if (cfgService.isAutoTradeEnabled()) {
				atService.autoTrade("kadir");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
