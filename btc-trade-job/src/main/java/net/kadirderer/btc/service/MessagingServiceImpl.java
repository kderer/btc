package net.kadirderer.btc.service;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;

@Service
public class MessagingServiceImpl implements MessagingService {
	
	@Autowired
	private SQSConnectionFactory sqsConnectionFactory;
	
	@Override
	public void sendMessage(String queueName, String message) {
		try {
			SQSConnection connection = sqsConnectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			// Create a queue identity with name 'TestQueue' in the session
			Queue queue = session.createQueue(queueName);
			 
			// Create a producer for the 'TestQueue'.
			MessageProducer producer = session.createProducer(queue);
			// Create the text message.
			TextMessage textMessage = session.createTextMessage(message);
			 
			// Send the message.
			producer.send(textMessage);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
