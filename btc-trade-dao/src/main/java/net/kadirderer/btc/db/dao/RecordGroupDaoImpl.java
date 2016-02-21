package net.kadirderer.btc.db.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import net.kadirderer.btc.db.model.RecordGroup;
import net.kadirderer.btc.db.repository.RecordGroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordGroupDaoImpl implements RecordGroupDao {
	
	@Autowired
	private RecordGroupRepository recordGroupRepository;

	@Override
	public RecordGroup insert(RecordGroup recordGroup) {
		recordGroup.setRecordTime(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("GMT+8"))));
		return recordGroupRepository.save(recordGroup);
	}

	@Override
	public List<RecordGroup> queryBetweenTime(Timestamp startTime,
			Timestamp endTime, Integer mod, List<Integer> platformIdList) {
		return recordGroupRepository.queryBetweenTime(startTime, endTime, mod, platformIdList);
	}

	@Override
	public RecordGroup queryById(Integer groupId) {
		return recordGroupRepository.findOne(groupId);
	}	

	@Override
	public Integer getRecordGroupCountBetweenTime(Timestamp startTime,
			Timestamp endTime, List<Integer> platformIdList) {
		
		return recordGroupRepository.getRecordGroupCountBetweenTime(startTime, endTime, platformIdList);
	}

	@Override
	public Long getAllRecordCount() {
		return recordGroupRepository.getAllRecordCount();
	}

	@Override
	public Integer getRecordGroupCountBetweenTime(Timestamp startTime,
			Timestamp endTime) {
		return recordGroupRepository.getRecordGroupCountBetweenTime(startTime, endTime);
	}	
	
}
