package net.kadirderer.btc.util.email;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailSendService {

	@Autowired
	private JavaMailSender mailer;
	
	@Async
	public void sendMail(Email email) {
		try {
			MimeMessage msg = mailer.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg);
			helper.setFrom(email.getFrom());
			helper.setTo(email.getToListArray());
			helper.setText(email.getBody());
			helper.setSubject(email.getSubject());

			mailer.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMailForException(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		
		Email email = new Email();
		email.addToToList("kderer@hotmail.com");
		email.setSubject("BTC Exception");
		email.setFrom("exception@btc.kadirderer.net");
		email.setBody(sw.toString());
		
		sendMail(email);
	}

}
