package net.kadirderer.btc.service;

import java.util.List;

import net.kadirderer.btc.db.model.AskBidRecord;
import net.kadirderer.btc.db.model.RecordGroup;

public interface AskBidRecordService {
	
	public AskBidRecord queryFromPlatform(Integer platformId) throws Exception;
	
	public List<AskBidRecord> queryFromPlatformList(List<Integer> platformList)  throws Exception;
	
	public List<AskBidRecord> insertWithRecordGroup(List<Integer> platformList, RecordGroup group) throws Exception;

}
