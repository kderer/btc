package net.kadirderer.btc.api.sellorder;

import net.kadirderer.btc.db.model.UserOrder;

public interface SellOrderService {
	
	public SellOrderResult sellOrder(UserOrder order) throws Exception;

}
