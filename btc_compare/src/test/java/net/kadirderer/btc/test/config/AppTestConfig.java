package net.kadirderer.btc.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = {"net.kadirderer.btc.util.email"})
@PropertySource(value = "classpath:app-config-test.properties")
public class AppTestConfig {
	
	@Value("${exchangerate.query.url}")
	private String exchangeRateQueryUrl;
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }    

	public String getExchangeRateQueryUrl() {
		return exchangeRateQueryUrl;
	}

}
