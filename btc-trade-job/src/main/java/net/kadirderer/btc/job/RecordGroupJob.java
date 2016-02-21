package net.kadirderer.btc.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class RecordGroupJob extends QuartzJobBean {
	
	private RecordGroupQueryTask recordGroupQueryTask;	
	
	public void setRecordGroupQueryTask(RecordGroupQueryTask recordGroupQueryTask) {
		this.recordGroupQueryTask = recordGroupQueryTask;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		recordGroupQueryTask.createRecordGroup();
	}

}
