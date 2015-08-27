package net.kadirderer.btc.config;

import java.util.HashMap;
import java.util.Map;

import net.kadirderer.btc.job.AutoTradeJob;
import net.kadirderer.btc.job.AutoTradeTask;
import net.kadirderer.btc.job.FailedSellOrderJob;
import net.kadirderer.btc.job.FailedSellOrderTask;
import net.kadirderer.btc.job.RecordGroupJob;
import net.kadirderer.btc.job.RecordGroupQueryTask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class JobConfiguration {
	
	@Bean
	public RecordGroupQueryTask recordGroupQueryTask() {
		return new RecordGroupQueryTask();
	}
    
    @Bean
	public AutoTradeTask autoTradeTask() {
		return new AutoTradeTask();
	}
    
    @Bean
    public FailedSellOrderTask failedSellOrderTask() {
    	return new FailedSellOrderTask();
    }
	
	@Bean
	public JobDetailFactoryBean setupRecordGroupJob() {
		JobDetailFactoryBean jobDetailBean = new JobDetailFactoryBean();
		
		jobDetailBean.setJobClass(RecordGroupJob.class);
		jobDetailBean.setDurability(true);
		
		return jobDetailBean;
	}
	
	@Bean
	public JobDetailFactoryBean setupAutoTradeJob() {
		JobDetailFactoryBean jobDetailBean = new JobDetailFactoryBean();
		
		jobDetailBean.setJobClass(AutoTradeJob.class);
		jobDetailBean.setDurability(true);
		
		return jobDetailBean;
	}
	
	@Bean
	public JobDetailFactoryBean setupFailedSellOrderJob() {
		JobDetailFactoryBean jobDetailBean = new JobDetailFactoryBean();
		
		jobDetailBean.setJobClass(FailedSellOrderJob.class);
		jobDetailBean.setDurability(true);
		
		return jobDetailBean;
	}
	
	@Bean
	public SimpleTriggerFactoryBean setupRecordGroupTrigger() {
		SimpleTriggerFactoryBean recordGroupQueryTrigger = new SimpleTriggerFactoryBean();
		
		recordGroupQueryTrigger.setJobDetail(setupRecordGroupJob().getObject());
		recordGroupQueryTrigger.setRepeatInterval(60000);		
		recordGroupQueryTrigger.setStartDelay(1000);
				
		return recordGroupQueryTrigger;
	}
	
	@Bean
	public SimpleTriggerFactoryBean setupAutoTradeTrigger() {
		SimpleTriggerFactoryBean recordGroupQueryTrigger = new SimpleTriggerFactoryBean();
		
		recordGroupQueryTrigger.setJobDetail(setupAutoTradeJob().getObject());
		recordGroupQueryTrigger.setRepeatInterval(3 * 60 * 1000);		
		recordGroupQueryTrigger.setStartDelay(1000);
				
		return recordGroupQueryTrigger;
	}
	
	@Bean
	public SimpleTriggerFactoryBean setupFailedSellOrderTrigger() {
		SimpleTriggerFactoryBean failedSellOrderTrigger = new SimpleTriggerFactoryBean();
		
		failedSellOrderTrigger.setJobDetail(setupFailedSellOrderJob().getObject());
		failedSellOrderTrigger.setRepeatInterval(6 * 60 * 1000);		
		failedSellOrderTrigger.setStartDelay(60 * 1000);
				
		return failedSellOrderTrigger;
	}
	
	@Bean
	public SchedulerFactoryBean setupSchedulerFactoryBean() {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		
		bean.setJobDetails(setupRecordGroupJob().getObject(), setupAutoTradeJob().getObject(),
				setupFailedSellOrderJob().getObject());
		bean.setTriggers(setupRecordGroupTrigger().getObject(), setupAutoTradeTrigger().getObject(),
				setupFailedSellOrderTrigger().getObject());
		bean.setWaitForJobsToCompleteOnShutdown(true);
		
		Map<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put("recordGroupQueryTask", recordGroupQueryTask());
		taskMap.put("autoTradeTask", autoTradeTask());
		taskMap.put("failedSellOrderTask", failedSellOrderTask());
		
		bean.setSchedulerContextAsMap(taskMap);
		
		return bean;
	}

}