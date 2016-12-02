package net.kadirderer.btc.impl.updateorder;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.cancelorder.CancelOrderService;
import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.api.updateorder.UpdateOrderResult;
import net.kadirderer.btc.api.updateorder.UpdateOrderService;
import net.kadirderer.btc.db.dao.FailedOrderDao;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.db.model.FailedOrder;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.impl.buyorder.BtcChinaBuyOrderResult;
import net.kadirderer.btc.impl.cancelorder.BtcChinaCancelOrderResult;
import net.kadirderer.btc.impl.sellorder.BtcChinaSellOrderResult;
import net.kadirderer.btc.util.enumaration.OrderStatus;
import net.kadirderer.btc.util.enumaration.OrderType;

@Service
public class BtcChinaUpdateOrderService implements UpdateOrderService {	
	
	@Autowired
	private CancelOrderService cancelOrderService;
	
	@Autowired
	private BuyOrderService buyOrderService;
	
	@Autowired
	private SellOrderService sellOrderService;
	
	@Autowired
	private UserOrderDao uoDao;
	
	@Autowired
	private FailedOrderDao failedOrderDao;
	
	@Override
	public UpdateOrderResult updateOrder(UserOrder order, double amount, double price)
			throws Exception {
		
		BtcChinaCancelOrderResult cancelOrderResult = (BtcChinaCancelOrderResult) cancelOrderService.cancelOrder(
				order.getUsername(), order.getReturnId());
		
		BtcChinaUpdateOrderResult result = new BtcChinaUpdateOrderResult();
		
		if (cancelOrderResult.getError() != null) {
			result.setError(cancelOrderResult.getError());
			return result;
		}
		
		Thread.sleep(2 * 1000);
		
		order.setAmount(amount);
		order.setPrice(price);
		
		if (order.getOrderType() == OrderType.BUY.getCode()) {
			BtcChinaBuyOrderResult boResult = (BtcChinaBuyOrderResult) buyOrderService.buyOrder(order);
			result.setError(cancelOrderResult.getError());
			result.setId(boResult.getResult());
		}
		else {
			BtcChinaSellOrderResult soResult = (BtcChinaSellOrderResult) sellOrderService.sellOrder(order);
			result.setError(cancelOrderResult.getError());
			result.setId(soResult.getResult());
		}
		
		order.setUpdateDate(Calendar.getInstance().getTime());
		order.setReturnId(result.getId());
		
		if (result.getError() != null) {
			order.setStatus(OrderStatus.FAILED.getCode());
		}
		
		uoDao.save(order);
		
		if (order.getStatus() == OrderStatus.FAILED.getCode()) {
			FailedOrder fo = new FailedOrder();
			fo.setMessage(result.getError().getMessage());
			fo.setErrorCode(result.getError().getCode());
			fo.setUserOrderId(order.getId());
			
			failedOrderDao.save(fo);
		}
		
		return result;
	}

}
