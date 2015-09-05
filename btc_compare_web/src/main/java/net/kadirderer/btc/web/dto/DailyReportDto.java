package net.kadirderer.btc.web.dto;

import java.util.ArrayList;
import java.util.List;

import net.kadirderer.btc.db.model.DailyReportDetail;

public class DailyReportDto {

	private String date;
	private List<DailyReportDetail> detailList;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<DailyReportDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<DailyReportDetail> detailList) {
		this.detailList = detailList;
	}
	
	public void addDailyReportDetail(DailyReportDetail dailyDetailReport) {
		if (detailList == null) {
			detailList = new ArrayList<DailyReportDetail>();
		}
		
		detailList.add(dailyDetailReport);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof DailyReportDto)) {
			return false;
		}
		
		return this.date.equals(((DailyReportDto)obj).getDate());
	}
}
