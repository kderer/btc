package net.kadirderer.btc.web.controller;

import java.util.List;

import net.kadirderer.btc.service.ReportService;
import net.kadirderer.btc.web.dto.DailyReportDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@RequestMapping(value = "dailyprofit", method = RequestMethod.GET)
	public String dailyProfitReport(ModelMap model) {
		
		List<DailyReportDto> reportList = reportService.queryDailyReport();
		model.addAttribute("dailyProfitList", reportList);
		
		return "report/dailyreport";
	}

}
