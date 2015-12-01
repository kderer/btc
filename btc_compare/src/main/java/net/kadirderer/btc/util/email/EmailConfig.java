package net.kadirderer.btc.util.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {
	
	@Value("${email.smtp.host}")
	private String smtpHost;
	
	@Value("${email.smtp.port}")
	private Integer smtpPort;
	
	@Value("${email.smtp.username}")
	private String smtpUsername;
	
	@Value("${email.smtp.password}")
	private String smtpPassword;
	
	@Value("${email.mail.smtp.auth}")
	private boolean smtpAuth;
	
	@Value("${email.mail.smtp.starttls.enable}")
	private boolean starttlsEnable;
	
	@Value("${email.mail.smtp.socketFactory.class}")
	private String socketFactoryClass;
	
	@Bean
	public JavaMailSender mailer() {
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", smtpAuth);
		javaMailProperties.put("mail.smtp.starttls.enable", starttlsEnable);		
		javaMailProperties.put("mail.smtp.socketFactory.class", socketFactoryClass);
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(smtpHost);
		mailSender.setPort(smtpPort);		
		mailSender.setUsername(smtpUsername);
		mailSender.setPassword(smtpPassword);
		mailSender.setJavaMailProperties(javaMailProperties);
		
		return mailSender;
	}
	
	public String getSmtpHost() {
		return smtpHost;
	}
	
	public Integer getSmtpPort() {
		return smtpPort;
	}
	
	public String getSmtpUsername() {
		return smtpUsername;
	}
	
	public String getSmtpPassword() {
		return smtpPassword;
	}
	
	public boolean isSmtpAuth() {
		return smtpAuth;
	}
	
	public boolean isStarttlsEnable() {
		return starttlsEnable;
	}
	
	public String getSocketFactoryClass() {
		return socketFactoryClass;
	}
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
