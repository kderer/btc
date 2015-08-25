package net.kadirderer.btc.service;

import net.kadirderer.btc.db.dao.UserOrderDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserOrderServiceImpl implements UserOrderService {
	
	@Autowired
	private UserOrderDao userOrderDao;

	@Override
	@Transactional
	public void addProfit(double profit, int platformId, String orderId) {
		userOrderDao.addProfit(profit, platformId, orderId);
	}

}
