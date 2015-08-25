package net.kadirderer.btc.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class FailedSellOrderJob extends QuartzJobBean {
	
	private FailedSellOrderTask failedSellOrderTask;	
	
	public void setFailedSellOrderTask(FailedSellOrderTask failedSellOrderTask) {
		this.failedSellOrderTask = failedSellOrderTask;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		failedSellOrderTask.autoTradeFailedSellOrder();
	}

}
