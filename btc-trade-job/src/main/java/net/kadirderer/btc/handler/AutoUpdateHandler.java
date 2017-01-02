package net.kadirderer.btc.handler;

import java.util.Calendar;

import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.service.AutoTradeService;
import net.kadirderer.btc.util.configuration.ConfigurationService;
import net.kadirderer.btc.util.enumaration.OrderStatus;

public class AutoUpdateHandler implements Runnable {
	
	private AutoTradeService autoTradeService;
	private ConfigurationService cfgService;
	private int userOrderId;
	private OrderEvoluateHandler orderEvoluateHandler;
	
	public static void handle(AutoTradeService autoTradeService, int userOrderId, ConfigurationService cfgService) {
		AutoUpdateHandler handler = new AutoUpdateHandler();
		handler.autoTradeService = autoTradeService;
		handler.userOrderId = userOrderId;
		handler.cfgService = cfgService;

		Thread thread = new Thread(handler);
		thread.start();
	}
	
	private AutoUpdateHandler() {
		
	}

	@Override
	public void run() {
		try {
			UserOrder uo = autoTradeService.findUserOrderById(userOrderId);
			
			if (uo.getStatus() == OrderStatus.NEW.getCode()) {
				uo.setStatus(OrderStatus.PENDING.getCode());
			}
			
			autoTradeService.saveUserOrder(uo);		
			
			long interval = cfgService.getAutoUpdateCheckInterval() * 1000;			
			
			while ((uo.getStatus() == OrderStatus.PENDING.getCode() || uo.getStatus() == OrderStatus.UPDATING.getCode()) &&
					cfgService.isAutoTradeEnabled()) {
				
				try {
					int retryCount = 0;
					while (uo.getStatus() == OrderStatus.UPDATING.getCode()) {				
						Thread.sleep(interval);				
						uo = autoTradeService.findUserOrderById(userOrderId);
						
						if (uo.getStatus() == OrderStatus.PENDING.getCode()) {
							retryCount = 0;
						}
						
						if (retryCount == 5) {
							uo.setStatus(OrderStatus.CANCELLED.getCode());
							autoTradeService.saveUserOrder(uo);
							return;
						}
						
						retryCount += 1;
					}
					
					if (Calendar.getInstance().getTimeInMillis() - uo.getCreateDate().getTime() >= interval && 
							(orderEvoluateHandler == null || !orderEvoluateHandler.isProcessing())) {
						
						if (uo.getReturnId() == null || uo.getReturnId().length() == 0) {
							return;
						}
						
						autoTradeService.queryOrder(uo.getUsername(), uo.getReturnId(), true);					
						orderEvoluateHandler = OrderEvoluateHandler.evoluate(autoTradeService, uo.getId(), cfgService);
					}
					
					Thread.sleep(interval);
					
					uo = autoTradeService.findUserOrderById(userOrderId);
				} catch (Exception e) {
					e.printStackTrace();
					uo = autoTradeService.findUserOrderById(userOrderId);
				}
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
