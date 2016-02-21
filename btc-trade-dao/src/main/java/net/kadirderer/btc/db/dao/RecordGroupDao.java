package net.kadirderer.btc.db.dao;

import java.sql.Timestamp;
import java.util.List;

import net.kadirderer.btc.db.model.RecordGroup;

public interface RecordGroupDao {
	
	public RecordGroup insert(RecordGroup recordGroup);
	
	public RecordGroup queryById(Integer groupId);
	
	public List<RecordGroup> queryBetweenTime(Timestamp startTime, Timestamp endTime, Integer mod, List<Integer> platformIdList);	
	
	public Integer getRecordGroupCountBetweenTime(Timestamp startTime,  Timestamp endTime);
	
	public Integer getRecordGroupCountBetweenTime(Timestamp startTime,  Timestamp endTime, List<Integer> platformIdList);
	
	public Long getAllRecordCount();

}
