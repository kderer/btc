package net.kadirderer.btc.statistics.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import net.kadirderer.btc.statistics.job.StatisticsJob;
import net.kadirderer.btc.statistics.job.StatisticsTask;

@Configuration
@ComponentScan(basePackages = {"net.kadirderer.btc.statistics.service"})
@PropertySource(value = "classpath:statistics-job-config.properties")
public class ApplicationConfig {
	
	@Bean
	public StatisticsTask statisticsTask() {
		return new StatisticsTask();
	}
	
	@Bean
	public JobDetailFactoryBean setupStatisticsJob() {
		JobDetailFactoryBean jobDetailBean = new JobDetailFactoryBean();
		
		jobDetailBean.setJobClass(StatisticsJob.class);
		jobDetailBean.setDurability(true);
		
		return jobDetailBean;
	}
	
	@Bean
	public SimpleTriggerFactoryBean setupStatisticsTrigger() {
		SimpleTriggerFactoryBean recordGroupQueryTrigger = new SimpleTriggerFactoryBean();
		
		recordGroupQueryTrigger.setJobDetail(setupStatisticsJob().getObject());
		recordGroupQueryTrigger.setRepeatInterval(10 * 1000);		
		recordGroupQueryTrigger.setStartDelay(30 * 1000);
				
		return recordGroupQueryTrigger;
	}
	
	@Bean
	public SchedulerFactoryBean setupSchedulerFactoryBean() {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		
		bean.setJobDetails(setupStatisticsJob().getObject());
		bean.setTriggers(setupStatisticsTrigger().getObject());
		bean.setWaitForJobsToCompleteOnShutdown(true);
		
		Map<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put("statisticsTask", statisticsTask());
		
		bean.setSchedulerContextAsMap(taskMap);
		
		return bean;
	}


}
