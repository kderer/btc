package net.kadirderer.btc.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import net.kadirderer.btc.db.DatabaseConfig;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class WebAppInitializer implements WebApplicationInitializer {

  	@Override
	public void onStartup(ServletContext container) throws ServletException {
  		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
  		appContext.register(ApplicationConfig.class);
  		appContext.register(DatabaseConfig.class);
  		appContext.register(PlatformApiConfig.class);
  		appContext.register(JobConfiguration.class);  
		
  		container.addListener(new ContextLoaderListener(appContext));  		
	}

}
