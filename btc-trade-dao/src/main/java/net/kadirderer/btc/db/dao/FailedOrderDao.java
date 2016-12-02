package net.kadirderer.btc.db.dao;

import net.kadirderer.btc.db.model.FailedOrder;

public interface FailedOrderDao {
	
	public FailedOrder save(FailedOrder failedOrder);
	
	public FailedOrder findByUserOrderId(int userOrderId);

}
