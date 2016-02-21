package net.kadirderer.btc.util.test.email;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.kadirderer.btc.util.email.Email;
import net.kadirderer.btc.util.email.MailSendService;
import net.kadirderer.btc.util.test.config.BaseTestConfig;

public class MailSendServiceTest extends BaseTestConfig {
	
	@Autowired
	private MailSendService mailSendService;
	
	@Test
	public void testSendMail() {
		Email mail = new Email();
		mail.addToToList("kderer@hotmail.com");
		mail.setFrom("test@btc.kadirderer.net");
		mail.setBody("Body");
		mail.setSubject("Subject");
		
		mailSendService.sendMail(mail);
	}
	

}
