package net.kadirderer.btc.web.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.kadirderer.btc.db.model.Statistics;
import net.kadirderer.btc.db.mybatis.domain.DailyProfitDetail;
import net.kadirderer.btc.service.ReportService;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@RequestMapping(value = "dailyprofit", method = RequestMethod.GET)
	public String dailyProfitReport(ModelMap model) {
		
		List<DailyProfitDetail> dailyProfitList = reportService.queryDailyProfit();
		model.addAttribute("dailyProfitList", dailyProfitList);
		
		return "report/dailyreport";
	}
	
	@RequestMapping(value = "statistics", method = RequestMethod.GET)
	public String statistics(ModelMap model) {		
		return "tiles.statistics";
	}
	
	@RequestMapping(value = "statisticsData", method = RequestMethod.GET)
	@ResponseBody
	public List<Statistics> statisticsData(ModelMap model) {
		
		List<Statistics> latestStatistics = reportService.findLatestNStatistics(30);
		
		Collections.reverse(latestStatistics);
		
		return latestStatistics;
	}

}
