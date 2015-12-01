package net.kadirderer.btc.util.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {
	
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
			
		}
	}

}
