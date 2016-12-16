package net.kadirderer.btc.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class BidCheckerJob extends QuartzJobBean {
	
	private BidCheckerTask bidCheckerTask;	
	
	public void setBidCheckerTask(BidCheckerTask bidCheckerTask) {
		this.bidCheckerTask = bidCheckerTask;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		bidCheckerTask.sweep();
	}

}
