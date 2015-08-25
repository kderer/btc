package net.kadirderer.btc.db.dao;

import java.util.Calendar;
import java.util.Date;

import net.kadirderer.btc.db.model.FailedSellOrder;
import net.kadirderer.btc.db.repository.FailedOrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FailedSellOrderDaoImpl implements FailedSellOrderDao {

	@Autowired
	private FailedOrderRepository failedOrderRepository;
	
	@Override
	public FailedSellOrder save(FailedSellOrder failedOrder) {
		if(failedOrder.getId() == null) {
			failedOrder.setCreateDate(Calendar.getInstance().getTime());
		} else {
			failedOrder.setUpdateDate(Calendar.getInstance().getTime());
		}
		return failedOrderRepository.save(failedOrder);
	}

	@Override
	public FailedSellOrder findFirstPending(Date earlierThan) {
		return failedOrderRepository.findFirstPending(earlierThan);
	}
	
	

}
