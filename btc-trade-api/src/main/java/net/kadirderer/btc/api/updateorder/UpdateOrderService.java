package net.kadirderer.btc.api.updateorder;

import net.kadirderer.btc.db.model.UserOrder;

public interface UpdateOrderService {
	
	public UpdateOrderResult updateOrder(UserOrder order, double amount, double price) throws Exception;

}
