package net.kadirderer.btc.config;

import net.kadirderer.btc.service.CacheService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

@Configuration
@ComponentScan(basePackages = {"net.kadirderer.btc.service", "net.kadirderer.btc.exchangerate"})
@PropertySource(value = "classpath:app-config.properties")
public class ApplicationConfig {
	
	@Value("${exchangerate.query.url}")
	private String exchangeRateQueryUrl;
	
	@Value("${exchangerate.currency.selected}")
	private String selectedCurrency;
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
	@Bean(initMethod = "init")
	public CacheService createCacheService() {
		CacheService cacheService = new CacheService();		
		return cacheService;
	}
	
	public String getExchangeRateQueryUrl() {
		return exchangeRateQueryUrl;
	}
	
	public String getSelectedCurrency() {
		return selectedCurrency;
	}
	
	@Bean
	public SQSConnectionFactory sqsConnectionFactory() {
		SQSConnectionFactory connectionFactory = SQSConnectionFactory.builder()
			.withRegion(Region.getRegion(Regions.AP_NORTHEAST_1))
			.withAWSCredentialsProvider(new SystemPropertiesCredentialsProvider())
			.build();
		
		return connectionFactory;
	}
	
}
