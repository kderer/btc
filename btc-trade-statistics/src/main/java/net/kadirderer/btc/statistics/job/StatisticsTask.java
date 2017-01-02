package net.kadirderer.btc.statistics.job;

import org.springframework.beans.factory.annotation.Autowired;

import net.kadirderer.btc.statistics.service.StatisticsService;

public class StatisticsTask {
	
	@Autowired
	private StatisticsService statisticsService;
	
	public void createNewStatistics() {
		try {
			statisticsService.createNewStatistics("kadir");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

}
