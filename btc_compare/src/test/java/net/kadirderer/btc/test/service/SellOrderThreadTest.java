package net.kadirderer.btc.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.kadirderer.btc.service.SellOrderThread;
import net.kadirderer.btc.test.config.AppTestConfig;
import net.kadirderer.btc.util.email.EmailSendService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppTestConfig.class})
public class SellOrderThreadTest {
	
	@Autowired
	private EmailSendService emailSendService;
	
	@Test
	public void sellOrderTest() {
		SellOrderThread sellOrderThread = new SellOrderThread();
		sellOrderThread.setEmailSendService(emailSendService);
		
		sellOrderThread.run();		
	}

}
