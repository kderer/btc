package net.kadirderer.btc.test.config;

import javax.jms.Connection;
import javax.jms.MessageListener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;

@Configuration
@ComponentScan(basePackages = {"net.kadirderer.btc.service"})
@PropertySource(value = "classpath:job-test-config.properties")
public class ApplicationTestConfig {
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
	@Bean
	public SQSConnectionFactory sqsConnectionFactory() {
		return null;
	}
	
	@Bean
	public Connection sqsConnection() {
		return null;
		
	}
	
	@Bean
	public MessageListener configSetMessageListener() {
		return null;
	}

}
