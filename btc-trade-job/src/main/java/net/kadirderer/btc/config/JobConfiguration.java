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
import net.kadirderer.btc.job.SweeperJob;
import net.kadirderer.btc.job.SweeperTask;

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
	public SweeperTask sweeperTask() {
		return new SweeperTask();
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
	public JobDetailFactoryBean setupSweeperJob() {
		JobDetailFactoryBean jobDetailBean = new JobDetailFactoryBean();
		
		jobDetailBean.setJobClass(SweeperJob.class);
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
		recordGroupQueryTrigger.setRepeatInterval(1 * 60 * 1000);		
		recordGroupQueryTrigger.setStartDelay(1000);
				
		return recordGroupQueryTrigger;
	}
	
	@Bean
	public SimpleTriggerFactoryBean setupSweeperTrigger() {
		SimpleTriggerFactoryBean recordGroupQueryTrigger = new SimpleTriggerFactoryBean();
		
		recordGroupQueryTrigger.setJobDetail(setupSweeperJob().getObject());
		recordGroupQueryTrigger.setRepeatInterval(3 * 60 * 60 * 1000);		
		recordGroupQueryTrigger.setStartDelay(5 * 60 * 1000);
				
		return recordGroupQueryTrigger;
	}
	
	@Bean
	public SchedulerFactoryBean setupSchedulerFactoryBean() {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		
		bean.setJobDetails(setupRecordGroupJob().getObject(), setupAutoTradeJob().getObject(),
				setupSweeperJob().getObject());
		bean.setTriggers(setupRecordGroupTrigger().getObject(), setupAutoTradeTrigger().getObject(),
				setupSweeperTrigger().getObject());
		bean.setWaitForJobsToCompleteOnShutdown(true);
		
		Map<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put("recordGroupQueryTask", recordGroupQueryTask());
		taskMap.put("autoTradeTask", autoTradeTask());
		taskMap.put("sweeperTask", sweeperTask());
		
		bean.setSchedulerContextAsMap(taskMap);
		
		return bean;
	}

}