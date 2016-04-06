package net.kadirderer.btc.db.dao;

import java.util.List;

import net.kadirderer.btc.db.criteria.UserOrderCriteria;
import net.kadirderer.btc.db.model.UserOrder;

public interface UserOrderDao {
	
	public UserOrder save(UserOrder userOrder);
	
	public List<UserOrder> findByUsername(String username);
	
	public List<UserOrder> findPending(String username, int platformId);
	
	public List<UserOrder> findByCriteria(UserOrderCriteria criteria);
	
	public long findByCriteriaCount(UserOrderCriteria criteria);
	
	public UserOrder findLastPending(String username, int platformId, char ordertype);
	
	public UserOrder findByOrderId(String username, int platformId, String orderId);
	
	public void updatePartnerId(int userOrderId, int partnerUserOrderId);
	
	public void updatePartnerIdWithNewId(int oldUserOrderId, int newUserOrderId);
	
}
