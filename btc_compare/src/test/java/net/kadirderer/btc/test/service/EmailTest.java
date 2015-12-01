package net.kadirderer.btc.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.kadirderer.btc.test.config.AppTestConfig;
import net.kadirderer.btc.util.email.Email;
import net.kadirderer.btc.util.email.EmailSendService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppTestConfig.class})
public class EmailTest {
	
	@Autowired
	private EmailSendService emailSendService;
	
	@Test
	public void testSendEmail() {
		Email mail = new Email();
		
		mail.addToToList("kderer@hotmail.com");
		mail.setFrom("exception@btc.kadirderer.net");
		mail.setSubject("exception test");
		mail.setBody("exception body");
		
		emailSendService.sendMail(mail);
	}
	

}
