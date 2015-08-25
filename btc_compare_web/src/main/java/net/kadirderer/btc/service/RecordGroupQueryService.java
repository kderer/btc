package net.kadirderer.btc.service;

import net.kadirderer.btc.web.dto.RecordGroupLineChartDataResponse;
import net.kadirderer.btc.web.dto.RecordGroupQuery;

public interface RecordGroupQueryService {
	
	public RecordGroupLineChartDataResponse queryAllPlatformsBetweenTimes(String startTime, String endTime, int mode);
	
	public RecordGroupLineChartDataResponse queryByOptions(RecordGroupQuery queryOptions);

}
