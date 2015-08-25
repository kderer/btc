package net.kadirderer.btc.db.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.kadirderer.btc.db.criteria.UserOrderCriteria;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.db.repository.UserOrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserOrderDaoImpl implements UserOrderDao {

	@Autowired
	private UserOrderRepository uoRepository;
	
	@PersistenceContext
	private EntityManager entityManager;	
	
	@Override
	public UserOrder save(UserOrder userOrder) {
		return uoRepository.save(userOrder);
	}

	@Override
	public List<UserOrder> findByUsername(String username) {
		return uoRepository.findByUsername(username);
	}

	@Override
	public List<UserOrder> findPending(String username, int platformId) {
		return uoRepository.findPending(username, platformId);
	}

	@Override
	public UserOrder findLastPending(String username, int platformId, char ordertype) {
		return uoRepository.findLastPending(username, platformId, ordertype);
	}

	@Override
	public UserOrder findByOrderId(String username, int platformId, String orderId) {
		return uoRepository.findByOrderId(username, platformId, orderId);
	}

	@Override
	public void addProfit(double profit, int platformId, String orderId) {
		uoRepository.addProfit(profit, platformId, orderId);
	}

	@Override
	public List<UserOrder> findByCriteria(UserOrderCriteria criteria) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserOrder> criteriaQuery = criteriaBuilder.createQuery(UserOrder.class);
		Root<UserOrder> from = criteriaQuery.from(UserOrder.class);
		
		Predicate predicate = prepareQuery(criteria, criteriaBuilder, from);		
		criteriaQuery.where(predicate);
		criteriaQuery.orderBy(criteriaBuilder.desc(from.get("createDate")));
		
		TypedQuery<UserOrder> typedQuery = entityManager.createQuery(criteriaQuery);		
		typedQuery.setFirstResult(criteria.getPageNumber() * criteria.getPageSize());
		typedQuery.setMaxResults(criteria.getPageSize());
		
		return typedQuery.getResultList();
	}

	@Override
	public long findByCriteriaCount(UserOrderCriteria criteria) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<UserOrder> from = countQuery.from(UserOrder.class);
		
		Predicate predicate = prepareQuery(criteria, criteriaBuilder, from);		
		countQuery.where(predicate);
			
		countQuery.select(criteriaBuilder.count(from));
		return entityManager.createQuery(countQuery).getSingleResult();
	}
	
	private Predicate prepareQuery(UserOrderCriteria criteria, CriteriaBuilder criteriaBuilder, 
			Root<UserOrder> from) {
		
		Predicate predicate = criteriaBuilder.conjunction();
		
		if(criteria.getAmountEnd() != null) {
			predicate = criteriaBuilder.and(criteriaBuilder.le(from.get("amount"), criteria.getAmountStart()), predicate);
		}		
		
		if(criteria.getAmountStart() != null) {
			predicate = criteriaBuilder.and(criteriaBuilder.ge(from.get("amount"), criteria.getAmountStart()), predicate);
		}
		
		if(criteria.getCompletedAmountEnd() != null) {
			predicate = criteriaBuilder.and(criteriaBuilder.le(from.get("completedAmount"), 
					criteria.getCompletedAmountEnd()), predicate);
		}
		
		if(criteria.getCompletedAmountStart() != null) {
			predicate = criteriaBuilder.and(criteriaBuilder.ge(from.get("completedAmount"),
					criteria.getCompletedAmountStart()), predicate);
		}
		
		if(criteria.getCreateDateEnd() != null) {
			Predicate datePredicate = criteriaBuilder.lessThanOrEqualTo(from.<Date>get("createDate"), 
					criteria.getCreateDateEnd());
			
			predicate = criteriaBuilder.and(datePredicate, predicate);
		}
		
		if(criteria.getCreateDateStart() != null) {
			Predicate datePredicate = criteriaBuilder.greaterThanOrEqualTo(from.<Date>get("createDate"), 
					criteria.getCreateDateStart());
			
			predicate = criteriaBuilder.and(datePredicate, predicate);
		}
		
		if(criteria.getIdList() != null && criteria.getIdList().size() > 0) {
			predicate = criteriaBuilder.and(from.get("id").in(criteria.getIdList()), predicate);
		}
		
		if(criteria.getOrderTypeList() != null && criteria.getOrderTypeList().size() > 0) {
			predicate = criteriaBuilder.and(from.get("orderType").in(criteria.getOrderTypeList()), predicate);
		}
		
		if(criteria.getPlatformIdList() != null && criteria.getPlatformIdList().size() > 0) {
			predicate = criteriaBuilder.and(from.get("platformId").in(criteria.getPlatformIdList()), predicate);
		}
		
		if(criteria.getPriceEnd() != null) {
			predicate = criteriaBuilder.and(criteriaBuilder.le(from.get("price"),
					criteria.getPriceEnd()), predicate);
		}
		
		if(criteria.getPriceStart() != null) {
			predicate = criteriaBuilder.and(criteriaBuilder.ge(from.get("price"),
					criteria.getPriceStart()), predicate);
		}
		
		if(criteria.getProfitEnd() != null) {
			predicate = criteriaBuilder.and(criteriaBuilder.le(from.get("profit"),
					criteria.getProfitEnd()), predicate);
		}
		
		if(criteria.getProfitStart() != null) {
			predicate = criteriaBuilder.and(criteriaBuilder.ge(from.get("profit"),
					criteria.getProfitStart()), predicate);
		}
		
		if(criteria.getReturnIdList() != null && criteria.getReturnIdList().size() > 0) {
			predicate = criteriaBuilder.and(from.get("returnId").in(criteria.getReturnIdList()), predicate);
		}
		
		if(criteria.getStatusList() != null && criteria.getStatusList().size() > 0) {
			predicate = criteriaBuilder.and(from.get("status").in(criteria.getStatusList()), predicate);
		}
		
		if(criteria.getUpdateDateEnd() != null) {
			Predicate datePredicate = criteriaBuilder.lessThanOrEqualTo(from.<Date>get("updateDate"), 
					criteria.getUpdateDateEnd());
			
			predicate = criteriaBuilder.and(datePredicate, predicate);
		}
		
		if(criteria.getUpdateDateStart() != null) {
			Predicate datePredicate = criteriaBuilder.greaterThanOrEqualTo(from.<Date>get("createDate"), 
					criteria.getUpdateDateStart());
			
			predicate = criteriaBuilder.and(datePredicate, predicate);
		}
		
		if(criteria.getUsernameList() != null && criteria.getUsernameList().size() > 0) {
			predicate = criteriaBuilder.and(from.get("username").in(criteria.getUsernameList()), predicate);
		}
		
		return predicate;
	}

}
