package net.kadirderer.btc.api.buyorder;

import net.kadirderer.btc.db.model.UserOrder;

public interface BuyOrderService {
	
	public BuyOrderResult buyOrder(UserOrder order) throws Exception;

}
