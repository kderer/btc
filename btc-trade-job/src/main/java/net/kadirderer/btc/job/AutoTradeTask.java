package net.kadirderer.btc.job;

import org.springframework.beans.factory.annotation.Autowired;

import net.kadirderer.btc.config.ConfigMap;
import net.kadirderer.btc.service.AutoTradeService;

public class AutoTradeTask {
	
	@Autowired
	private AutoTradeService atService;
	
	
	public void autoTrade() {
		try {
			if (ConfigMap.isAutoTradeEnabled()) {
				atService.autoTrade("kadir");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
