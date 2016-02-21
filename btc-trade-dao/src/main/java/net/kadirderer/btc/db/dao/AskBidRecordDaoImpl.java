package net.kadirderer.btc.db.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import net.kadirderer.btc.db.model.AskBidRecord;
import net.kadirderer.btc.db.repository.AskBidRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AskBidRecordDaoImpl implements AskBidRecordDao {
	
	@Autowired
	private AskBidRecordRepository askBidRecordRepository;

	@Override
	public List<AskBidRecord> queryAllRecords() {		
		return askBidRecordRepository.findAll();
	}

	@Override
	public List<AskBidRecord> queryLastHours(int hour) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AskBidRecord> queryTimeInterval(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AskBidRecord insert(AskBidRecord askBidRecord) {
		askBidRecord.setRecordate(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("GMT+8"))));
		return askBidRecordRepository.save(askBidRecord);
	}

	@Override
	public Integer obtainRecordGroupId() {
		return askBidRecordRepository.obtainRecordGroupId();
	}

	@Override
	public List<AskBidRecord> insertList(List<AskBidRecord> list) {
		for(AskBidRecord record : list) {
			record.setRecordate(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("GMT+8"))));
			askBidRecordRepository.save(record);
		}
		return list;
	}

}
