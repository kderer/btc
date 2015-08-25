package net.kadirderer.btc.job;

import net.kadirderer.btc.service.RecordGroupService;

import org.springframework.beans.factory.annotation.Autowired;

public class RecordGroupQueryTask {
	
	@Autowired
	private RecordGroupService recordGroupService;
	
	public void createRecordGroup() {
		try {
			recordGroupService.createRecordForAllPlatforms();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
