package net.kadirderer.btc.service;

import java.util.ArrayList;
import java.util.List;

import net.kadirderer.btc.db.dao.ReportDao;
import net.kadirderer.btc.db.model.DailyReportDetail;
import net.kadirderer.btc.db.mybatis.domain.DailyProfitDetail;
import net.kadirderer.btc.db.mybatis.persistence.ReportMapper;
import net.kadirderer.btc.web.dto.DailyReportDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ReportDao reportDao;
	
	@Autowired
	private ReportMapper reportMapper;
	
	@Override
	public List<DailyReportDto> queryDailyReport() {
		List<DailyReportDetail> reportDetailList = reportDao.queryDailyProfit();
		
		List<DailyReportDto> dtoList = new ArrayList<DailyReportDto>();
		
		for (DailyReportDetail drd : reportDetailList) {
			
			DailyReportDto dto = new DailyReportDto();
			dto.setDate(drd.getDate());			
			
			boolean isDtoExist = false;
			for(DailyReportDto dailyReportDto : dtoList) {
				if(dto.equals(dailyReportDto)) {
					dailyReportDto.addDailyReportDetail(drd);
					isDtoExist = true;
					break;
				}
			}
			
			if(!isDtoExist) {
				dto.addDailyReportDetail(drd);
				dtoList.add(dto);
			}
		}
		
		return dtoList;
	}

	@Override
	public List<DailyProfitDetail> queryDailyProfit() {
		return reportMapper.queryDailyProfit();
	}
	
	

}
