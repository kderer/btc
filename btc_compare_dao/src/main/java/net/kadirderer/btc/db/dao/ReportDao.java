package net.kadirderer.btc.db.dao;

import java.util.List;

import net.kadirderer.btc.db.model.DailyReportDetail;

public interface ReportDao {
	
	public List<DailyReportDetail> queryDailyProfit();

}
