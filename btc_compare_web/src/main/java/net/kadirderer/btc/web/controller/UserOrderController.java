package net.kadirderer.btc.web.controller;

import net.kadirderer.btc.api.OrderStatus;
import net.kadirderer.btc.api.OrderType;
import net.kadirderer.btc.db.criteria.UserOrderCriteria;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.service.CacheService;
import net.kadirderer.btc.service.UserOrderService;
import net.kadirderer.btc.web.dto.DatatableAjaxResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class UserOrderController {
	
	@Autowired
	private UserOrderService userOrderService;
	
	@Autowired
	private CacheService cacheService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model) {		
		model.addAttribute("platformList", cacheService.getPlatformList());
		
		return "order/list_order";
	}
	
	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.GET)	
	public DatatableAjaxResponse<UserOrder> query(@RequestParam("length") int pageSize, 
			@RequestParam("start") int start, @RequestParam("draw") int draw,
			@ModelAttribute UserOrderCriteria criteria) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		criteria.addUsername(auth.getName());
		criteria.setPageSize(pageSize);
		criteria.setPageNumber(start / pageSize);
		
		DatatableAjaxResponse<UserOrder> response = userOrderService.query(criteria);
		response.setDraw(draw);
		
		return response;
	}
	
	@ModelAttribute("statusList")
	public OrderStatus[] statusList() {
		return OrderStatus.values();
	}
	
	@ModelAttribute("orderTypeList")
	public OrderType[] orderTypeList() {
		return OrderType.values();
	}

}
