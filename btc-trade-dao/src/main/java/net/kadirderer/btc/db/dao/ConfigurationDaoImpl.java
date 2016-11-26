package net.kadirderer.btc.db.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.db.model.Configuration;
import net.kadirderer.btc.db.repository.ConfigurationRepository;

@Service		
public class ConfigurationDaoImpl implements ConfigurationDao {

	@Autowired
	private ConfigurationRepository cfgRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Configuration save(Configuration configuration) {
		return cfgRepository.save(configuration);
	}

	@Override
	public Page<Configuration> query(int pageSize, int pageNumber) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Configuration> criteriaQuery = criteriaBuilder.createQuery(Configuration.class);		
		criteriaQuery.from(Configuration.class);
		
		TypedQuery<Configuration> typedQuery = entityManager.createQuery(criteriaQuery);		
		typedQuery.setFirstResult(pageNumber * pageSize);
		typedQuery.setMaxResults(pageSize);
		
		CriteriaQuery<Long> countQuery = entityManager.getCriteriaBuilder().createQuery(Long.class);
		countQuery.select(criteriaBuilder.count(countQuery.from(Configuration.class)));
		
		PageRequest request = new PageRequest(pageNumber, pageSize);		
		long total = entityManager.createQuery(countQuery).getSingleResult();				
		PageImpl<Configuration> result = new PageImpl<Configuration>(typedQuery.getResultList(), request, total);
		
		return result;
	}

	@Override
	public Configuration findByName(String name) {
		return cfgRepository.findByName(name);
	}

	@Override
	public void delete(int id) {
		cfgRepository.delete(id);
	}

	@Override
	public Configuration findById(int id) {
		return cfgRepository.findOne(id);
	}
	
}
