package net.kadirderer.btc.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SweeperJob extends QuartzJobBean {
	
	private AutoTradeTask autoTradeTask;	
	
	public void setAutoTradeTask(AutoTradeTask autoTradeTask) {
		this.autoTradeTask = autoTradeTask;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		autoTradeTask.autoTrade();
	}

}
