package net.kadirderer.btc.web.dto;

import java.util.List;

public class RecordGroupQuery {
	
	private String startTime;
	private String endTime;
	private List<Integer> platformIdList;
	private String currency;
	private Integer recordAmount;
	
	
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
	
	public List<Integer> getPlatformIdList() {
		return platformIdList;
	}

	public void setPlatformIdList(List<Integer> platformIdList) {
		this.platformIdList = platformIdList;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getRecordAmount() {
		return recordAmount;
	}

	public void setRecordAmount(Integer recordAmount) {
		this.recordAmount = recordAmount;
	}
	
}
