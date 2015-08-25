package net.kadirderer.btc.db.dao;

import java.util.Date;

import net.kadirderer.btc.db.model.FailedSellOrder;

public interface FailedSellOrderDao {
	
	public FailedSellOrder save(FailedSellOrder failedOrder);
	
	public FailedSellOrder findFirstPending(Date earlierThan);

}
