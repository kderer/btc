package net.kadirderer.btc.db.dao;

import java.util.List;

import net.kadirderer.btc.db.model.Statistics;

public interface StatisticsDao {
	
	public List<Statistics> findLatestNStatistics(int n);
	
	public Statistics save(Statistics statistics);

}
