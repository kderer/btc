package net.kadirderer.btc.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

}
