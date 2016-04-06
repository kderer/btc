package net.kadirderer.btc.service;

import java.util.List;

import net.kadirderer.btc.db.mybatis.domain.DailyProfitDetail;
import net.kadirderer.btc.web.dto.DailyReportDto;

public interface ReportService {
	
	public List<DailyReportDto> queryDailyReport();
	
	public List<DailyProfitDetail> queryDailyProfit();

}
