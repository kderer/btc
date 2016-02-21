package net.kadirderer.btc.util.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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
	
}
