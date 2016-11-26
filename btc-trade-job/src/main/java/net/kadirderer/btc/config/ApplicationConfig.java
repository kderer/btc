package net.kadirderer.btc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"net.kadirderer.btc.service"})
@PropertySource(value = "classpath:job-config.properties")
public class ApplicationConfig {	


}
