package net.kadirderer.btc.web.controller;

import java.util.Map;

import javax.validation.constraints.NotNull;

import net.kadirderer.btc.service.ConfigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/config")
public class ConfigController {
	
	@Autowired
	private ConfigService configService;	
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model) {
		
		Map<String, String> configValueMap = configService.receiveConfigValues();
		
		model.addAttribute("recordgroupjob_enabled", configValueMap.get("recordgroupjob_enabled"));		
		model.addAttribute("autotrade_enabled", configValueMap.get("autotrade_enabled"));
		model.addAttribute("sellorder_check_period", configValueMap.get("sellorder_check_period"));
		model.addAttribute("sellorder_delta", configValueMap.get("sellorder_delta"));
		model.addAttribute("sellorder_time_limit", configValueMap.get("sellorder_time_limit"));
		model.addAttribute("buyorder_time_limit", configValueMap.get("buyorder_time_limit"));
		model.addAttribute("buyorder_delta", configValueMap.get("buyorder_delta"));
		model.addAttribute("buyorder_check_period", configValueMap.get("buyorder_check_period"));
		
//		model.addAttribute("recordgroupjob_enabled", "true");		
//		model.addAttribute("autotrade_enabled", "ture");
//		model.addAttribute("sellorder_check_period", "ture");
//		model.addAttribute("sellorder_delta", "ture");
//		model.addAttribute("sellorder_time_limit", "ture");
//		model.addAttribute("buyorder_time_limit", "ture");
//		model.addAttribute("buyorder_delta", "ture");
//		model.addAttribute("buyorder_check_period", "ture");
		
		return "config/list_config";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String addPlatformApi(ModelMap model, 
			@RequestParam(value = "recordgroupjob_enabled") @NotNull String recordgroupjob_enabled, 
			@RequestParam(value = "autotrade_enabled") @NotNull String autotrade_enabled,
			@RequestParam(value = "sellorder_delta") @NotNull String sellorder_delta,
			@RequestParam(value = "sellorder_check_period") @NotNull String sellorder_check_period,
			@RequestParam(value = "sellorder_time_limit") @NotNull String sellorder_time_limit,
			@RequestParam(value = "buyorder_time_limit") @NotNull String buyorder_time_limit,
			@RequestParam(value = "buyorder_delta") @NotNull String buyorder_delta,
			@RequestParam(value = "buyorder_check_period") @NotNull String buyorder_check_period) {
		
		Map<String, String> configValueMap = configService.receiveConfigValues();
		
		updateValue(configValueMap, "recordgroupjob_enabled", recordgroupjob_enabled);
		updateValue(configValueMap, "autotrade_enabled", autotrade_enabled);
		updateValue(configValueMap, "sellorder_delta", sellorder_delta);
		updateValue(configValueMap, "sellorder_check_period", sellorder_check_period);
		updateValue(configValueMap, "sellorder_time_limit", sellorder_time_limit);
		updateValue(configValueMap, "buyorder_time_limit", buyorder_time_limit);
		updateValue(configValueMap, "buyorder_delta", buyorder_delta);
		updateValue(configValueMap, "buyorder_check_period", buyorder_check_period);

		return "config/list_config";
	}
	
	
	private void updateValue(Map<String, String> valueMap, String configName, String newValue) {
		
		String oldValue = valueMap.get(configName);
		
		if(oldValue != null && newValue != null && !oldValue.equals(newValue)) {
			configService.setConfig(configName, newValue);
		}
	}

}
