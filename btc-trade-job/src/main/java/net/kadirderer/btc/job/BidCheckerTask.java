package net.kadirderer.btc.job;

import org.springframework.beans.factory.annotation.Autowired;

import net.kadirderer.btc.service.BidCheckerService;
import net.kadirderer.btc.util.configuration.ConfigurationService;

public class BidCheckerTask {
	
	@Autowired
	private BidCheckerService bidCheckerService;
	
	@Autowired
	private ConfigurationService cfgService;
	
	public void sweep() {
		try {
			if (cfgService.isAutoTradeEnabled()) {
				bidCheckerService.checkGMOB("kadir");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
