package net.kadirderer.btc.service;

import java.util.List;

import net.kadirderer.btc.db.model.RecordGroup;

public interface RecordGroupService {
	
	public RecordGroup insertRecordGroup(List<Integer> platformList) throws Exception;
	
	public RecordGroup createRecordForAllPlatforms() throws Exception;

}
