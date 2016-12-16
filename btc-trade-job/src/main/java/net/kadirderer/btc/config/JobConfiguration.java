package net.kadirderer.btc.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import net.kadirderer.btc.job.AutoTradeJob;
import net.kadirderer.btc.job.AutoTradeTask;
import net.kadirderer.btc.job.RecordGroupJob;
import net.kadirderer.btc.job.RecordGroupQueryTask;
import net.kadirderer.btc.job.BidCheckerJob;
import net.kadirderer.btc.job.BidCheckerTask;

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
	public BidCheckerTask bidCheckerTask() {
		return new BidCheckerTask();
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
	public JobDetailFactoryBean setupBidCheckerJob() {
		JobDetailFactoryBean jobDetailBean = new JobDetailFactoryBean();
		
		jobDetailBean.setJobClass(BidCheckerJob.class);
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
		recordGroupQueryTrigger.setRepeatInterval(1 * 5 * 1000);		
		recordGroupQueryTrigger.setStartDelay(1000);
				
		return recordGroupQueryTrigger;
	}
	
	@Bean
	public SimpleTriggerFactoryBean setupBidCheckerTrigger() {
		SimpleTriggerFactoryBean recordGroupQueryTrigger = new SimpleTriggerFactoryBean();
		
		recordGroupQueryTrigger.setJobDetail(setupBidCheckerJob().getObject());
		recordGroupQueryTrigger.setRepeatInterval(10 * 1000);		
		recordGroupQueryTrigger.setStartDelay(30 * 1000);
				
		return recordGroupQueryTrigger;
	}
	
	@Bean
	public SchedulerFactoryBean setupSchedulerFactoryBean() {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		
		bean.setJobDetails(setupRecordGroupJob().getObject(), setupAutoTradeJob().getObject(),
				setupBidCheckerJob().getObject());
		bean.setTriggers(setupRecordGroupTrigger().getObject(), setupAutoTradeTrigger().getObject(),
				setupBidCheckerTrigger().getObject());
		bean.setWaitForJobsToCompleteOnShutdown(true);
		
		Map<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put("recordGroupQueryTask", recordGroupQueryTask());
		taskMap.put("autoTradeTask", autoTradeTask());
		taskMap.put("bidCheckerTask", bidCheckerTask());
		
		bean.setSchedulerContextAsMap(taskMap);
		
		return bean;
	}

}