package net.kadirderer.btc.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.kadirderer.btc.db.dao.RecordGroupDao;
import net.kadirderer.btc.db.model.AskBidRecord;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.RecordGroup;
import net.kadirderer.btc.exchangerate.ExchangeRateQuery;
import net.kadirderer.btc.exchangerate.ExchangeRateQueryResult;
import net.kadirderer.btc.exchangerate.ExchangeRateQueryService;
import net.kadirderer.btc.web.dto.RecordGroupLineChartDataResponse;
import net.kadirderer.btc.web.dto.RecordGroupQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordGroupQueryServiceImpl implements RecordGroupQueryService {

	@Autowired
	private RecordGroupDao recordGroupDao;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private ExchangeRateQueryService exchangeRateQueryService;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
	
	@Override
	public RecordGroupLineChartDataResponse queryAllPlatformsBetweenTimes(
			String startTime, String endTime, int mode) {
		
		Timestamp start = Timestamp.valueOf(startTime);
		Timestamp end = Timestamp.valueOf(endTime);
		
		List<RecordGroup> resultList = recordGroupDao.queryBetweenTime(start, end, mode, null);
		
		String[] platformCodes = generatePlatformIdStringArray(cacheService.getPlatformList());
		
		String[] timeStringArray = generateTimeStringArray(resultList);
		
		Map<String, List<Double>> platfromAskDataMatrix = generatePlatformDataMatrix(cacheService.getPlatformList(),
				resultList, "TRY",  true);
		
		Map<String, List<Double>> platfromBidDataMatrix = generatePlatformDataMatrix(cacheService.getPlatformList(),
				resultList, "TRY", false);
		
		RecordGroupLineChartDataResponse response = new RecordGroupLineChartDataResponse();
		
		response.setTimes(timeStringArray);
		response.setPlatformCodes(platformCodes);
		response.setPlatformAskpricelistMap(platfromAskDataMatrix);
		response.setPlatformBidpricelistMap(platfromBidDataMatrix);
		
		return response;
	}
	
	@Override
	public RecordGroupLineChartDataResponse queryByOptions(
			RecordGroupQuery queryOptions) {		
		
		Timestamp startTime = Timestamp.valueOf(LocalDateTime.parse(queryOptions.getStartTime(), DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")));
		Timestamp endTime = Timestamp.valueOf(LocalDateTime.parse(queryOptions.getEndTime(), DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")));
		
		int totalRecordCount = recordGroupDao.getRecordGroupCountBetweenTime(startTime, endTime);
		
		int mode = 1;
		if(totalRecordCount > queryOptions.getRecordAmount()) {
			mode = (int)Math.round((double)totalRecordCount / (double)queryOptions.getRecordAmount());
		}
		
		List<RecordGroup> resultList = recordGroupDao.queryBetweenTime(startTime, endTime, mode, queryOptions.getPlatformIdList());
		
		int dif = resultList.size() - queryOptions.getRecordAmount();
		if(mode == 1 && dif > 0) {
			int removeIndex = 1;
			for(int i = 0; i < dif; i++) {
				resultList.remove(removeIndex);
			}
		}
		
		List<BtcPlatform> platformList = getPlatformList(queryOptions.getPlatformIdList());
		
		String[] platformCodes = generatePlatformIdStringArray(platformList);
		
		String[] timeStringArray = generateTimeStringArray(resultList);
		
		Map<String, List<Double>> platfromAskDataMatrix = generatePlatformDataMatrix(platformList, resultList,
				queryOptions.getCurrency(), true);
		
		Map<String, List<Double>> platfromBidDataMatrix = generatePlatformDataMatrix(platformList, resultList,
				queryOptions.getCurrency(), false);
		
		RecordGroupLineChartDataResponse response = new RecordGroupLineChartDataResponse();		
		response.setTimes(timeStringArray);
		response.setPlatformCodes(platformCodes);
		response.setPlatformAskpricelistMap(platfromAskDataMatrix);
		response.setPlatformBidpricelistMap(platfromBidDataMatrix);
		response.setCurrency(queryOptions.getCurrency());
		response.setStartTime(queryOptions.getStartTime());
		response.setEndTime(queryOptions.getEndTime());
		
		return response;
	}
	
	private List<BtcPlatform> getPlatformList(List<Integer> platformIdList) {
		List<BtcPlatform> platformList = new ArrayList<BtcPlatform>();
		
		for(Integer platformId : platformIdList) {
			platformList.add(cacheService.getPlatform(platformId));
		}
		
		return platformList;		
	}
	
	private String[] generatePlatformIdStringArray(List<BtcPlatform> platformList) {		
		
		String[] platfromCodes = new String[platformList.size()];
		
		int i = 0;
		for(BtcPlatform platform : platformList) {
			
			platfromCodes[i] = String.valueOf(platform.getCode());
			i += 1;			
		}
		
		return platfromCodes;
		
	}
	
	private String[] generateTimeStringArray(List<RecordGroup> recordGroupList) {		
		String[] timeStringArray = new String[recordGroupList.size()];
		
		int i = 0;
		int previousDayOfMonth = 0;
		Calendar recordGroupCalendar = Calendar.getInstance();
		for(RecordGroup recordGroup : recordGroupList) {
			recordGroupCalendar.setTime(recordGroup.getRecordTime());			
			if(previousDayOfMonth != recordGroupCalendar.get(Calendar.DAY_OF_MONTH)) {
				timeStringArray[i] = sdf.format(recordGroup.getRecordTime());
			}
			else {
				timeStringArray[i] = sdf2.format(recordGroup.getRecordTime());
			}
			previousDayOfMonth = recordGroupCalendar.get(Calendar.DAY_OF_MONTH);
			i += 1;
		}
		
		return timeStringArray;
	}
	
	private Map<String, List<Double>> generatePlatformDataMatrix(List<BtcPlatform> platformList, 
			List<RecordGroup> recordGroupList, String currency, boolean isAsk) {
		
		Map<String, List<Double>> platformDataMatrix = new HashMap<String, List<Double>>();
		
		for(RecordGroup group : recordGroupList) {
			
			for(BtcPlatform platform : platformList) {
				AskBidRecord record = group.getAskBidRecords().get(platform.getId());
				
				List<Double> recordListForPlatform = platformDataMatrix.get(platform.getCode());
				if(recordListForPlatform == null) {
					recordListForPlatform = new ArrayList<Double>();
					platformDataMatrix.put(platform.getCode(), recordListForPlatform);
				}
				
				if(record == null) {
					recordListForPlatform.add((double)0);
				}
				else if(isAsk) {
					recordListForPlatform.add(getConvertedPrice(currency, platform.getCurrency(),
							record.getAskRate(), group.getExchangeRates().getRatesMap()));
				}
				else {
					recordListForPlatform.add(getConvertedPrice(currency, platform.getCurrency(),
							record.getBidRate(), group.getExchangeRates().getRatesMap()));
				}
			}
		}
		
		return platformDataMatrix;
	}
	
	
	private Double getConvertedPrice(String resultCurrency, String orgCurrency, 
			double price, String exchangeRateMapJSON) {
		ExchangeRateQuery erq = new ExchangeRateQuery();
		erq.setFirstCurrency(resultCurrency);
		erq.setSecondCurrency(orgCurrency);
		
		ExchangeRateQueryResult erqr = exchangeRateQueryService.qetExchangeRates(erq, exchangeRateMapJSON);
		
		return price / erqr.getRateByFirst();
	}


	
	
	
}
