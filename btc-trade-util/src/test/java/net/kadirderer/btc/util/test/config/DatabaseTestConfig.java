package net.kadirderer.btc.util.test.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@PropertySource(value = "classpath:db-test-config.properties")
public class DatabaseTestConfig {
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
	@Bean
	public DataSource dataSource() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass(dbDriverClass);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		cpds.setJdbcUrl(dbConnectionUrl);
		cpds.setUser(dbUsername);
		cpds.setPassword(dbPassword);
		
		return cpds;
	}	
	
    @Value("${db.connection.jdbc.url}")
	private String dbConnectionUrl;
    
    @Value("${db.connection.username}")
    private String dbUsername;
    
    @Value("${db.connection.password}")
    private String dbPassword;
    
    @Value("${db.connection.driverClass}")
    private String dbDriverClass;   
    
}