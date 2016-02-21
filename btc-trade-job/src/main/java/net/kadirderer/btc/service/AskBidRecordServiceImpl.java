package net.kadirderer.btc.service;

import java.util.ArrayList;
import java.util.List;

import net.kadirderer.btc.api.orderbook.OrderbookResult;
import net.kadirderer.btc.api.orderbook.OrderbookService;
import net.kadirderer.btc.db.dao.AskBidRecordDao;
import net.kadirderer.btc.db.model.AskBidRecord;
import net.kadirderer.btc.db.model.RecordGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AskBidRecordServiceImpl implements AskBidRecordService {

	@Autowired
	private OrderbookService orderbookService;
	
	@Autowired
	private AskBidRecordDao askBidRecordDao;
	
	@Override
	public AskBidRecord queryFromPlatform(Integer platformId) throws Exception {
		
		OrderbookResult queryResult = orderbookService.query(platformId);
		
		AskBidRecord askBidRecord = new AskBidRecord();
		askBidRecord.setAskRate(queryResult.getHighestAsk());
		askBidRecord.setBidRate(queryResult.getHighestBid());
		askBidRecord.setPlatformId(platformId);
		
		return askBidRecord;
	}

	@Override
	public List<AskBidRecord> queryFromPlatformList(List<Integer> platformList) throws Exception {
		
		List<AskBidRecord> recordList = new ArrayList<AskBidRecord>();
		
		for(Integer platformId : platformList) {
			AskBidRecord record = queryFromPlatform(platformId);
			recordList.add(record);
		}
		
		return recordList;
	}

	@Override
	public List<AskBidRecord> insertWithRecordGroup(List<Integer> platformList, RecordGroup group) throws Exception {
		
		List<AskBidRecord> recordList = queryFromPlatformList(platformList);
		
		for(AskBidRecord askBidRecord : recordList) {
			askBidRecord.setRecordGroup(group);
			askBidRecordDao.insert(askBidRecord);
		}
		
		return recordList;
	}

	

}
