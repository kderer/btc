package net.kadirderer.btc.service;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	private SQSConnectionFactory sqsConnectionFactory;
	
	@Override
	public void setConfig(String configName, String configValue) {
		
		try {
			SQSConnection connection = sqsConnectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			// Create a queue identity with name 'TestQueue' in the session
			Queue queue = session.createQueue("btc_config_set_queue");
			 
			// Create a producer for the 'TestQueue'.
			MessageProducer producer = session.createProducer(queue);
			// Create the text message.
			TextMessage message = session.createTextMessage(configName + ":" + configValue);
			 
			// Send the message.
			producer.send(message);
			
			connection.stop();
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> receiveConfigValues() {
		try {
			
			setConfig("sendConfigValue", "true");
			
			SQSConnection connection = sqsConnectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);		
			
			// Create a queue identity with name 'TestQueue' in the session
			Queue queue = session.createQueue("btc_config_value_queue");
			
			// Create a consumer for the 'TestQueue'.
			MessageConsumer consumer = session.createConsumer(queue);
			 
			// Start receiving incoming messages.
			connection.start();
			
			// Receive a message from 'TestQueue' and wait up to 1 second
			Message receivedMessage = consumer.receive();
			 
			String mapString = ((TextMessage) receivedMessage).getText();
			
			connection.stop();
			
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(mapString, Map.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
}
