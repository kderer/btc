package net.kadirderer.btc.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.api.updateorder.UpdateOrderService;
import net.kadirderer.btc.db.criteria.UserOrderCriteria;
import net.kadirderer.btc.db.model.FailedOrder;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.impl.buyorder.BtcChinaBuyOrderResult;
import net.kadirderer.btc.impl.sellorder.BtcChinaSellOrderResult;
import net.kadirderer.btc.impl.updateorder.BtcChinaUpdateOrderResult;
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
	private BuyOrderService buyOrderService;
	
	@Autowired
	private UpdateOrderService updateOrderService;
	
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
			WebUtil.addMessage(model, "message.listorder.cancelorder.successfull", true);			
		} catch(Exception e) {
			WebUtil.addMessage(model, "message.listorder.cancelorder.error", false);
		}
		
		UserOrder cancelOrder = userOrderService.findUserOrder(userOrderId);
		model.addAttribute("cancelOrder", cancelOrder);
		
		return "order/cancel_order";
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
		double amount = userOrder.getAmount();
		
		try {
			BtcChinaUpdateOrderResult result = (BtcChinaUpdateOrderResult) updateOrderService.updateOrder(
					userOrder, amount, price);
			
			if (result.getError() != null) {
				throw new Exception();
			}			
		} catch (Exception e) {
			WebUtil.addMessage(model, "message.listorder.updateorder.error", false);
			return "order/update_order";
		}
		
		WebUtil.addMessage(model, "message.listorder.updateorder.success", true);
		return "order/update_order";
	}
	
	@RequestMapping(value="failedOrder", method = RequestMethod.GET)
	public String failedOrder(ModelMap model, @RequestParam(value = "uoId") @NotNull int userOrderId) {
		
		FailedOrder fo = userOrderService.findFailedOrder(userOrderId);
		
		if (fo == null) {
			fo = new FailedOrder();
			fo.setRetryResult('T');
		}
		
		model.addAttribute("failedOrder", fo);

		return "order/failed_order";
	}
	
	@RequestMapping(value="retryOrder", method = RequestMethod.POST)
	public String retry(ModelMap model,
			@ModelAttribute("failedOrder") FailedOrder failedOrder,
			RedirectAttributes redirectAttributes) {		
		try {			
			failedOrder = userOrderService.findFailedOrder(failedOrder.getUserOrderId());			
			if (failedOrder == null) {
				failedOrder = new FailedOrder();	
				throw new Exception();
			}
			
			UserOrder order = userOrderService.findUserOrder(failedOrder.getUserOrderId());			
			if (order == null) {
				throw new Exception();
			}
			
			order.setId(null);
			failedOrder.setUserOrder(order);
			if (order.isAutoTrade() && order.isAutoUpdate()) {
				order.setStatus(OrderStatus.NEW.getCode());
			}
			
			if (order.getOrderType() == OrderType.BUY.getCode()) {
				BtcChinaBuyOrderResult result = (BtcChinaBuyOrderResult) buyOrderService.buyOrder(order);
				
				if (result.getError() != null) {					
					throw new Exception();					
				}				
			}
			else {
				BtcChinaSellOrderResult result = (BtcChinaSellOrderResult) sellOrderService.sellOrder(order);
				
				if (result.getError() != null) {
					throw new Exception();
				}
			}
		} catch (Exception e) {
			failedOrder.setRetryResult(OrderStatus.FAILED.getCode());			
			if (failedOrder.getId() != null) {
				userOrderService.saveFailedOrder(failedOrder);
			}
			
			WebUtil.addMessage(model, "message.listorder.failedorder.reorder.error", false);
			model.addAttribute("failedOrder", failedOrder);
			
			return "order/failed_order";
		}
		
		failedOrder.setRetryResult(OrderStatus.DONE.getCode());		
		userOrderService.saveFailedOrder(failedOrder);
		
		WebUtil.addMessage(model, "message.listorder.failedorder.reorder.success", true);
		model.addAttribute("failedOrder", failedOrder);

		return "order/failed_order";
	}
	
	@RequestMapping(value="detail", method = RequestMethod.GET)
	public String orderDetail(ModelMap model,
			@RequestParam(value = "uoId") @NotNull int userOrderId) throws Exception {
		UserOrder userOrder = userOrderService.findUserOrder(userOrderId);		
		model.addAttribute("order", userOrder);
		
		HashMap<String, List<Double>> dataMap = new HashMap<>();
		List<Double> gmoaList = userOrder.generateLastGmoaList();
		List<Double> gmobList = userOrder.generateLastGmobList();
		Collections.reverse(gmoaList);
		Collections.reverse(gmobList);
		
		List<Double> priceList = new ArrayList<>();
		List<Double> basePriceList = new ArrayList<>();
		List<Double> hgmobList = new ArrayList<>();
		for (int i = 0; i < gmoaList.size(); i++) {
			priceList.add(userOrder.getPrice());
			basePriceList.add(userOrder.getBasePrice());
			hgmobList.add(userOrder.getHighestGmob());
		}
		
		dataMap.put("GMOA", gmoaList);
		dataMap.put("GMOB", gmobList);
		dataMap.put("Order", priceList);
		dataMap.put("BasePrice", basePriceList);
		dataMap.put("HGMOB", basePriceList);
		
		ObjectMapper om = new ObjectMapper();
		model.addAttribute("graphData", om.writeValueAsString(dataMap));

		return "order/detail_order";
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
