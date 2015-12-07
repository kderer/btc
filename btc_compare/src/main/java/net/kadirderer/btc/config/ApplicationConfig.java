package net.kadirderer.btc.config;

import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

@Configuration
@ComponentScan(basePackages = { "net.kadirderer.btc.exchangerate", "net.kadirderer.btc.service",
		"net.kadirderer.btc.util.email" })
@PropertySource(value = "classpath:app-config.properties")
public class ApplicationConfig {	
		
	@Value("${exchangerate.query.url}")
	private String exchangeRateQueryUrl;
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }    

	public String getExchangeRateQueryUrl() {
		return exchangeRateQueryUrl;
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
	public Connection sqsConnection() {
		try {
			SQSConnection connection = sqsConnectionFactory().createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			// Create a queue identity with name 'TestQueue' in the session
			MessageConsumer consumer = session.createConsumer(session.createQueue("btc_config_set_queue"));
			
			consumer.setMessageListener(configSetMessageListener());
			
			connection.start();
			
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	@Bean
	public MessageListener configSetMessageListener() {
		ConfigMessageListener cml = new ConfigMessageListener();
		
		return cml;
	}

}
