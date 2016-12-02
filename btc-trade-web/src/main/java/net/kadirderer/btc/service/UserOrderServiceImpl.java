package net.kadirderer.btc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.api.cancelorder.CancelOrderService;
import net.kadirderer.btc.db.criteria.UserOrderCriteria;
import net.kadirderer.btc.db.dao.FailedOrderDao;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.db.model.FailedOrder;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.web.dto.DatatableAjaxResponse;

@Service
public class UserOrderServiceImpl implements UserOrderService {

	@Autowired
	private UserOrderDao userOrderDao;
	
	@Autowired
	private FailedOrderDao faileOrderDao;
	
	@Autowired
	private CancelOrderService cancelOrderService;
	
	@Override
	public List<UserOrder> findAllByUsername(String username) {
		return userOrderDao.findByUsername(username);
	}

	@Override
	public List<UserOrder> findByCriteria(UserOrderCriteria criteria) {
		return userOrderDao.findByCriteria(criteria);
	}

	@Override
	public long findByCriteriaCount(UserOrderCriteria criteria) {
		return userOrderDao.findByCriteriaCount(criteria);
	}

	@Override
	public DatatableAjaxResponse<UserOrder> query(UserOrderCriteria criteria) {
		DatatableAjaxResponse<UserOrder> dar = new DatatableAjaxResponse<UserOrder>();
		
		long totalRecord = findByCriteriaCount(criteria);
		
		dar.setRecordsTotal(totalRecord);
		dar.setRecordsFiltered(totalRecord);
		dar.setData(findByCriteria(criteria));
		
		return dar;
	}
	
	@Override
	public UserOrder findUserOrder(int userOrderId) {
		UserOrderCriteria criteria = new UserOrderCriteria();
		criteria.addId(userOrderId);
		return userOrderDao.findByCriteria(criteria).get(0);
	}

	@Override
	public void cancelOrder(String username, String orderId) throws Exception {
		cancelOrderService.cancelOrder(username, orderId);
	}

	@Override
	public FailedOrder findFailedOrder(int userOrderId) {
		FailedOrder fo = faileOrderDao.findByUserOrderId(userOrderId);
		
		if (fo != null) {
			fo.setUserOrder(userOrderDao.findById(userOrderId));			
		}
		
		return fo;
	}

	@Override
	public FailedOrder saveFailedOrder(FailedOrder order) {
		return faileOrderDao.save(order);
	}

}
