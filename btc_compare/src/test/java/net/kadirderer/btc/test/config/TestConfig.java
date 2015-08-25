package net.kadirderer.btc.test.config;

import net.kadirderer.btc.config.PlatformApiConfig;

import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public abstract class TestConfig {
	
	private boolean isInitialized = false;
	private AnnotationConfigApplicationContext context;
	
	@Before
	public void initialize() {
		
		if(!isInitialized) {
			context = new AnnotationConfigApplicationContext(DatabaseTestConfig.class, PlatformApiConfig.class);
			isInitialized = true;		
		}
		
		getObjectFromContext(context);
	}
	
	public abstract void getObjectFromContext(AnnotationConfigApplicationContext context);

}
