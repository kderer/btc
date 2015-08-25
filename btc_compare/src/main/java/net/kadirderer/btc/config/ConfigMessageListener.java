package net.kadirderer.btc.config;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import net.kadirderer.btc.service.MessagingService;

import org.springframework.beans.factory.annotation.Autowired;

public class ConfigMessageListener implements MessageListener {	

	@Autowired
	public MessagingService messagingService;

	@Override
	public void onMessage(Message arg0) {
		try {
			String message = ((TextMessage)arg0).getText();
			
			String[] configPair = message.split(":");
			String configName = null;
			String configValue = null;
			
			if(configPair != null && configPair.length == 2) {
				configName = configPair[0];
				configValue = configPair[1];
				
				if(!"sendConfigValue".equals(configName)) {
					ConfigMap.setKeyValue(configName, configValue);
				}				
				
				messagingService.sendMessage("btc_config_value_queue", ConfigMap.valueMapJson());				
			}
			
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
