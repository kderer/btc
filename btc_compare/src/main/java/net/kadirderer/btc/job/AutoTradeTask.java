package net.kadirderer.btc.job;

import net.kadirderer.btc.config.ConfigMap;
import net.kadirderer.btc.service.AutoTradeThreadService;

import org.springframework.beans.factory.annotation.Autowired;

public class AutoTradeTask {
	
	@Autowired
	private AutoTradeThreadService atService;
	
	
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
