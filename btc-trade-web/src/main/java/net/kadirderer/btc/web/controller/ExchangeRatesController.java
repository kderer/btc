package net.kadirderer.btc.web.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.kadirderer.exchangerate.query.ExchangeRateQueryService;
import net.kadirderer.exchangerate.query.ExchangeRateTable;

@Controller
@RequestMapping("/exchangeRates")
public class ExchangeRatesController {
	
	@Autowired
	private ExchangeRateQueryService erqs;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model) {
		return "redirect:/exchangeRates/rebase.html?currency=TRY";
	}
	
	
	@RequestMapping(value = "/rebase", method = RequestMethod.GET)
	public String newPlatform(ModelMap model, @RequestParam("currency") @NotNull String currency) {
		String exchangeRatesJson = erqs.queryExchangeRateJSON();
		ExchangeRateTable erTable = erqs.getExchangeRateTable(exchangeRatesJson);
		
		erTable = erqs.changeBase(erTable, currency);
		
		model.addAttribute("exchangeRatesTable", erTable);
		
		model.addAttribute("exchangeRatesJson", exchangeRatesJson);
		
		return "exchangerates/exchangerates";
	}

}
