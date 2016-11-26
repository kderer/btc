package net.kadirderer.btc.job;

import org.springframework.beans.factory.annotation.Autowired;

import net.kadirderer.btc.service.RecordGroupService;
import net.kadirderer.btc.util.configuration.ConfigurationService;

public class RecordGroupQueryTask {
	
	@Autowired
	private RecordGroupService recordGroupService;
	
	@Autowired
	private ConfigurationService configService;
	
	public void createRecordGroup() {
		try {
			if (configService.isRecordGroupJobEnabled()) {
				recordGroupService.createRecordForAllPlatforms();
			}			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
