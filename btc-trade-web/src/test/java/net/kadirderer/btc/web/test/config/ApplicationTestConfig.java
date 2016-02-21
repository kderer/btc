package net.kadirderer.btc.web.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;

import net.kadirderer.btc.service.CacheService;

@Configuration
@ComponentScan(basePackages = {"net.kadirderer.btc.service"})
@PropertySource(value = "classpath:app-test-config.properties")
public class ApplicationTestConfig {
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
	@Bean(initMethod = "init")
	public CacheService createCacheService() {
		CacheService cacheService = new CacheService();		
		return cacheService;
	}
	
	@Bean
	public SQSConnectionFactory sqsConnectionFactory() {
		return null;
	}

}
