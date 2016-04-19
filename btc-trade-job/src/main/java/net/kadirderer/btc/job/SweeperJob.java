package net.kadirderer.btc.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SweeperJob extends QuartzJobBean {
	
	private SweeperTask sweeperTask;	
	
	public void setSweeperTask(SweeperTask sweeperTask) {
		this.sweeperTask = sweeperTask;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		sweeperTask.sweep();
	}

}
