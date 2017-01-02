package net.kadirderer.btc.statistics.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class StatisticsJob extends QuartzJobBean {
	
	private StatisticsTask statisticsTask;	
	
	public void setStatisticsTask(StatisticsTask statisticsTask) {
		this.statisticsTask = statisticsTask;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		statisticsTask.createNewStatistics();
	}

}
