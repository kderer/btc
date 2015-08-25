package net.kadirderer.btc.db.dao;


import java.util.Date;
import java.util.List;

import net.kadirderer.btc.db.model.AskBidRecord;

public interface AskBidRecordDao {
	
	public AskBidRecord insert(AskBidRecord askBidRecord);
	
	public List<AskBidRecord> insertList(List<AskBidRecord> list);
	
	public List<AskBidRecord> queryAllRecords();
	
	public List<AskBidRecord> queryLastHours(int hour);
	
	public List<AskBidRecord> queryTimeInterval(Date startDate, Date endDate);
	
	public Integer obtainRecordGroupId();
	

}
