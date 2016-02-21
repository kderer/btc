package net.kadirderer.btc.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import net.kadirderer.btc.db.DatabaseConfig;
import net.kadirderer.btc.util.config.UtilConfig;
import net.kadirderer.exchangerate.config.ExchangeRateConfig;

public class WebAppInitializer implements WebApplicationInitializer {

  	@Override
	public void onStartup(ServletContext container) throws ServletException {
  		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
  		appContext.register(ApplicationConfig.class);
  		appContext.register(DatabaseConfig.class);
  		appContext.register(JobConfiguration.class);
  		appContext.register(TradeApiConfig.class);
  		appContext.register(UtilConfig.class);
  		appContext.register(ExchangeRateConfig.class);
		
  		container.addListener(new ContextLoaderListener(appContext));  		
	}

}
