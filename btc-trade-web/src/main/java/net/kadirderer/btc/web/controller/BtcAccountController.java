package net.kadirderer.btc.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoResult;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.impl.buyorder.BtcChinaBuyOrderResult;
import net.kadirderer.btc.impl.sellorder.BtcChinaSellOrderResult;
import net.kadirderer.btc.service.BtcAccountService;
import net.kadirderer.btc.service.CacheService;
import net.kadirderer.btc.web.dto.BuyOrderDto;
import net.kadirderer.btc.web.dto.SellOrderDto;
import net.kadirderer.btc.web.util.WebUtil;

@Controller
@RequestMapping("/btcAccount")
public class BtcAccountController {
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private BtcAccountService btcAccountService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String btcAccount(ModelMap model) {
		
		List<QueryAccountInfoResult> resultList = new ArrayList<QueryAccountInfoResult>();
		try {
			QueryAccountInfoResult result = btcAccountService.queryAccountInfo(getLoggedInUsername(), "BTCCHINA");
			model.addAttribute("btcAccountInfo", result);
			resultList.add(result);
			
			model.addAttribute("btcAccountInfoList", resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("btcAccountInfoList", resultList);
		model.addAttribute("buyOrder", new BuyOrderDto());
		model.addAttribute("sellOrder", new SellOrderDto());
		return "tiles.btcaccount";
	}
	
	public String getLoggedInUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	@RequestMapping(value="buyOrder", method = RequestMethod.POST)
	public String buyOrder(ModelMap model, @ModelAttribute("buyOrder") @Valid BuyOrderDto buyOrder,
			@RequestParam(required = false, name = "isAutoTrade") String isAutoTrade,
			RedirectAttributes redirectAttributes) {
		
		if (isAutoTrade != null && isAutoTrade.equals("on")) {
			buyOrder.setAutoTrade(true);
		}
		
		buyOrder.setUsername(getLoggedInUsername());
		
		try {
			BtcChinaBuyOrderResult result = (BtcChinaBuyOrderResult) btcAccountService.buyOrder(buyOrder);
			
			if (result.getError() == null) {				
				WebUtil.addRedirectMessage(redirectAttributes, "message.btcaccount.buyorder.successful", true);
			}
			else {
				WebUtil.addRedirectMessage(redirectAttributes, "message.btcaccount.buyorder.error", false);
			}			
		} catch (Exception e) {
			WebUtil.addRedirectMessage(redirectAttributes, "message.btcaccount.buyorder.error.exception", false);
		}
		
		return "redirect:/btcAccount.html";
	}
	
	@RequestMapping(value="sellOrder", method = RequestMethod.POST)
	public String sellOrder(ModelMap model, @ModelAttribute("sellOrder") @Valid SellOrderDto sellOrder,
			@RequestParam(required = false, name = "isAutoTrade") String isAutoTrade,			
			RedirectAttributes redirectAttributes) {
		
		if (isAutoTrade != null && isAutoTrade.equals("on")) {
			sellOrder.setAutoTrade(true);
		}
		
		sellOrder.setUsername(getLoggedInUsername());
		
		try {
			BtcChinaSellOrderResult result = (BtcChinaSellOrderResult) btcAccountService.sellOrder(sellOrder);
			
			if (result.getError() == null) {
				WebUtil.addRedirectMessage(redirectAttributes, "message.btcaccount.sellorder.successful", true);
			}
			else {
				WebUtil.addRedirectMessage(redirectAttributes, "message.btcaccount.sellorder.error", false);
			}			
		} catch (Exception e) {
			WebUtil.addRedirectMessage(redirectAttributes, "message.btcaccount.sellorder.error.exception", false);
		}
		
		return "redirect:/btcAccount.html";
	}
	
	@ModelAttribute("platformList")
	public List<BtcPlatform> platformList() {
		return cacheService.getPlatformList();
	}

}
