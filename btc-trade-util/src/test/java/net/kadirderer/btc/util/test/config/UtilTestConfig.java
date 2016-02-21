package net.kadirderer.btc.util.test.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;

import net.kadirderer.btc.util.email.EmailConfig;

@Configuration
@ComponentScan(basePackages = { "net.kadirderer.btc.util.email"})
@PropertySource(value = "classpath:util-test-config.properties")
@EnableAsync
public class UtilTestConfig {
	
	@Autowired
	private EmailConfig mailConfig;
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
	@Bean
	public JavaMailSender mailer() {
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", mailConfig.isSmtpAuth());
		javaMailProperties.put("mail.smtp.starttls.enable", mailConfig.isStarttlsEnable());		
		javaMailProperties.put("mail.smtp.socketFactory.class", mailConfig.getSocketFactoryClass());
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(mailConfig.getSmtpHost());
		mailSender.setPort(mailConfig.getSmtpPort());		
		mailSender.setUsername(mailConfig.getSmtpUsername());
		mailSender.setPassword(mailConfig.getSmtpPassword());
		mailSender.setJavaMailProperties(javaMailProperties);
		
		return mailSender;
	}

}
