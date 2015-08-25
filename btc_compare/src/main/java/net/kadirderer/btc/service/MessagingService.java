package net.kadirderer.btc.service;

public interface MessagingService {
	
	public void sendMessage(String queueName, String message);

}
