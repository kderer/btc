package net.kadirderer.btc.db.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.db.model.Statistics;
import net.kadirderer.btc.db.repository.StatisticsRepository;

@Service
public class StatisticsDaoImpl implements StatisticsDao {
	
	@Autowired
	private StatisticsRepository statisticsRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Statistics> findLatestNStatistics(int n) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Statistics> criteriaQuery = criteriaBuilder.createQuery(Statistics.class);
		Root<Statistics> from = criteriaQuery.from(Statistics.class);
		
		Predicate predicate = criteriaBuilder.conjunction();		
		criteriaQuery.where(predicate);
		criteriaQuery.orderBy(criteriaBuilder.desc(from.get("id")));
		
		TypedQuery<Statistics> typedQuery = entityManager.createQuery(criteriaQuery);		
		typedQuery.setFirstResult(1);
		typedQuery.setMaxResults(n);
		
		return typedQuery.getResultList();
	}

	@Override
	public Statistics save(Statistics statistics) {
		if (statistics.getId() == null) {
			statistics.setCreateTime(Calendar.getInstance().getTime());
		}
		return statisticsRepository.save(statistics);
	}	
}
