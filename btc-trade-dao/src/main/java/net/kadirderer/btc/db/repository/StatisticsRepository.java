package net.kadirderer.btc.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kadirderer.btc.db.model.Statistics;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
	
}
