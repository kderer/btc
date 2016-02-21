package net.kadirderer.btc.service;

import java.util.ArrayList;
import java.util.List;

import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.dao.RecordGroupDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.RecordGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecordGroupServiceImpl implements RecordGroupService {
	
	private static Logger logger = LoggerFactory.getLogger(RecordGroupServiceImpl.class);

	@Autowired
	private AskBidRecordService askBidRecordService;
	
	@Autowired
	private ExchangeRatesService exchangeRatesService;
	
	@Autowired
	private RecordGroupDao recordGroupDao;
	
	@Autowired
	private BtcPlatformDao btcPlatformDao;	
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public RecordGroup insertRecordGroup(List<Integer> platformList) throws Exception {		
		
		RecordGroup group = new RecordGroup();
		
		recordGroupDao.insert(group);		
		
		askBidRecordService.insertWithRecordGroup(platformList, group);
		
		exchangeRatesService.insertWithRecordGroup(group);
		
		logger.info("A new record group is created. Recorgroup id: {}", group.getRecordGroupId());
		
		return group;
	}


	@Override
	public RecordGroup createRecordForAllPlatforms() throws Exception {
		List<BtcPlatform> platformList = btcPlatformDao.queryAll();
		
		List<Integer> platformIdList = new ArrayList<Integer>();
		
		for(BtcPlatform platform : platformList) {
			platformIdList.add(platform.getId());
		}
		
		return insertRecordGroup(platformIdList);
	}

}
