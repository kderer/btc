package net.kadirderer.btc.service;

import java.util.List;

import net.kadirderer.btc.db.criteria.UserOrderCriteria;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.web.dto.DatatableAjaxResponse;

public interface UserOrderService {
	
	public List<UserOrder> findAllByUsername(String username);
	
	public List<UserOrder> findByCriteria(UserOrderCriteria criteria);
	
	public long findByCriteriaCount(UserOrderCriteria criteria);
	
	public DatatableAjaxResponse<UserOrder> query(UserOrderCriteria criteria);
}
