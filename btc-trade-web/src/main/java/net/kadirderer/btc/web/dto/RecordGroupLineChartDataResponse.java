package net.kadirderer.btc.web.dto;

import java.util.List;
import java.util.Map;


public class RecordGroupLineChartDataResponse {
	
	private String[] times;
	private String[] platformCodes;
	private Map<String, List<Double>> platformAskpricelistMap;
	private Map<String, List<Double>> platformBidpricelistMap;
	private String currency;
	private String startTime;
	private String endTime;
	
	public String[] getTimes() {
		return times;
	}
	
	public void setTimes(String[] times) {
		this.times = times;
	}
	
	public String[] getPlatformCodes() {
		return platformCodes;
	}
	
	public void setPlatformCodes(String[] platformCodes) {
		this.platformCodes = platformCodes;
	}

	public Map<String, List<Double>> getPlatformAskpricelistMap() {
		return platformAskpricelistMap;
	}

	public void setPlatformAskpricelistMap(
			Map<String, List<Double>> platformAskpricelistMap) {
		this.platformAskpricelistMap = platformAskpricelistMap;
	}

	public Map<String, List<Double>> getPlatformBidpricelistMap() {
		return platformBidpricelistMap;
	}

	public void setPlatformBidpricelistMap(
			Map<String, List<Double>> platformBidpricelistMap) {
		this.platformBidpricelistMap = platformBidpricelistMap;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
