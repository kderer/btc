package net.kadirderer.btc.web.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.cancelorder.CancelOrderService;
import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.db.criteria.UserOrderCriteria;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.impl.buyorder.BtcChinaBuyOrderResult;
import net.kadirderer.btc.impl.sellorder.BtcChinaSellOrderResult;
import net.kadirderer.btc.impl.util.NumberUtil;
import net.kadirderer.btc.service.CacheService;
import net.kadirderer.btc.service.UserOrderService;
import net.kadirderer.btc.util.enumaration.OrderStatus;
import net.kadirderer.btc.util.enumaration.OrderType;
import net.kadirderer.btc.web.dto.DatatableAjaxResponse;
import net.kadirderer.btc.web.dto.UpdateOrderDto;
import net.kadirderer.btc.web.util.WebUtil;

@Controller
@RequestMapping("/order")
public class UserOrderController {
	
	@Autowired
	private UserOrderService userOrderService;
	
	@Autowired
	private MarketDepthService marketDepthService;
	
	@Autowired
	private CancelOrderService cancelOrderService;
	
	@Autowired
	private BuyOrderService buyOrderService;
	
	@Autowired
	private SellOrderService sellOrderService;
	
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
	
	@RequestMapping(value="cancelOrder", method = RequestMethod.GET)
	public String cancelOrder(ModelMap model, @RequestParam(value = "uoId") @NotNull int userOrderId) {
		
		model.addAttribute("cancelOrder", userOrderService.findUserOrder(userOrderId));

		return "order/cancel_order";
	}
	
	@RequestMapping(value="cancelOrder", method = RequestMethod.POST)
	public String cancelOrder(ModelMap model, @RequestParam(value = "id") @NotNull int userOrderId,
			@RequestParam(value = "returnId") @NotNull String returnId, RedirectAttributes redirectAttributes) {
		
		try {
			userOrderService.cancelOrder(getLoggedInUsername(), returnId);
			WebUtil.addRedirectMessage(redirectAttributes, "message.listorder.cancelorder.successfull", true);
		} catch(Exception e) {
			WebUtil.addRedirectMessage(redirectAttributes, "message.listorder.cancelorder.error", false);
		}
		
		return "redirect:/order.html";
	}
	
	@RequestMapping(value="updateOrder", method = RequestMethod.GET)
	public String updateOrder(ModelMap model,
			@RequestParam(value = "uoId") @NotNull int userOrderId) throws Exception {
		UserOrder userOrder = userOrderService.findUserOrder(userOrderId);		
		double latestBid = marketDepthService.getMarketDepth(getLoggedInUsername()).getLowestAsk();		 
		
		UpdateOrderDto updateOrderDto = new UpdateOrderDto();
		updateOrderDto.setAmount(userOrder.getAmount());
		updateOrderDto.setCurrentPrice(latestBid);
		updateOrderDto.setPrice(userOrder.getPrice());
		updateOrderDto.setId(userOrder.getId());
		updateOrderDto.setReturnId(userOrder.getReturnId());
		updateOrderDto.setOrderType(userOrder.getOrderType());
		
		model.addAttribute("updateOrder", updateOrderDto);	

		return "order/update_order";
	}
	
	@RequestMapping(value="updateOrder", method = RequestMethod.POST)
	public String updateOrder(ModelMap model,
			@ModelAttribute("updateOrder") @Valid UpdateOrderDto updateOrderDto,
			RedirectAttributes redirectAttributes) throws Exception {
		
		UserOrder userOrder = userOrderService.findUserOrder(updateOrderDto.getId());
		
		double price = updateOrderDto.getPrice();
		
		UserOrder order = new UserOrder();
		order.setUsername(userOrder.getUsername());
		order.setBasePrice(userOrder.getBasePrice());
		order.setParentId(userOrder.getParentId());
		order.setAmount(userOrder.getAmount());
		order.setStatus(userOrder.getStatus());
		order.setPrice(price);
		
		try {
			cancelOrderService.cancelOrder(userOrder.getUsername(), userOrder.getReturnId());
			
			Thread.sleep(2 * 1000);
			
			if (userOrder.getOrderType() == OrderType.BUY.getCode()) {							
				double oldCost = userOrder.getPrice() * userOrder.getAmount();				
				double amount = NumberUtil.format(oldCost / price);
				
				order.setAmount(amount);
				
				BtcChinaBuyOrderResult result = (BtcChinaBuyOrderResult)buyOrderService.buyOrder(order);
				
				if (result.getError() != null) {
					throw new Exception();
				}				
			} else {				
				BtcChinaSellOrderResult result = (BtcChinaSellOrderResult)sellOrderService.sellOrder(order);
				
				if (result.getError() != null) {
					throw new Exception();
				}
			} 
		} catch (Exception e) {
			WebUtil.addMessage(model, "message.listorder.updateorder.error", false);
		}
		
		WebUtil.addMessage(model, "message.listorder.updateorder.success", true);
		return "order/update_order";
	}
	
	
	@ModelAttribute("statusList")
	public OrderStatus[] statusList() {
		return OrderStatus.values();
	}
	
	@ModelAttribute("orderTypeList")
	public OrderType[] orderTypeList() {
		return OrderType.values();
	}
	
	public String getLoggedInUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
