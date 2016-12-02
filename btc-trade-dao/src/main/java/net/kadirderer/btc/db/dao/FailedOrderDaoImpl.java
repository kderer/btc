package net.kadirderer.btc.db.dao;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.db.model.FailedOrder;
import net.kadirderer.btc.db.repository.FailedOrderRepository;

@Service
public class FailedOrderDaoImpl implements FailedOrderDao {

	@Autowired
	private FailedOrderRepository failedOrderRepository;
	
	@Override
	public FailedOrder save(FailedOrder failedOrder) {
		if(failedOrder.getId() == null) {
			failedOrder.setCreateDate(Calendar.getInstance().getTime());
		}
		return failedOrderRepository.save(failedOrder);
	}

	@Override
	public FailedOrder findByUserOrderId(int userOrderId) {
		return failedOrderRepository.findByUserOrderId(userOrderId);
	}
	
	

}
