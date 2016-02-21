package net.kadirderer.btc.web.controller;

import net.kadirderer.btc.service.CacheService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@Autowired
	private CacheService cacheService;
		
	@RequestMapping("/index")
	public String index(Model model) {
		
		model.addAttribute("platformList", cacheService.getPlatformList());
		
		return "index/index";
	}
	
}
