package net.kadirderer.btc.util;

import net.kadirderer.btc.service.AutoTradeThreadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SpringContextListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private AutoTradeThreadService autoTradeService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		try {
			autoTradeService.autoTrade("kadir");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
