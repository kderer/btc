package net.kadirderer.btc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

import net.kadirderer.btc.service.CacheService;
import net.kadirderer.btc.util.HashingUtil;

@Configuration
@ComponentScan(basePackages = {"net.kadirderer.btc.service"})
@PropertySource(value = "classpath:app-config.properties")
public class ApplicationConfig {
	
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
		SQSConnectionFactory connectionFactory = SQSConnectionFactory.builder()
			.withRegion(Region.getRegion(Regions.AP_NORTHEAST_1))
			.withAWSCredentialsProvider(new SystemPropertiesCredentialsProvider())
			.build();
		
		return connectionFactory;
	}
	
	@Bean
	public BCryptPasswordEncoder btcPasswordEncoder() {
		return HashingUtil.getBCryptPasswordEncoder();
	}
	
}
