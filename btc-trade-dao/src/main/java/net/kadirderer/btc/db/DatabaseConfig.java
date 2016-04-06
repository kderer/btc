package net.kadirderer.btc.db;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"net.kadirderer.btc.db.repository"})
@EnableTransactionManagement
@ComponentScan(basePackages = {"net.kadirderer.btc.db.dao"})
@MapperScan("net.kadirderer.btc.db.mybatis.persistence")
@PropertySource(value = "classpath:db-config.properties")
public class DatabaseConfig {
	
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
		
	@Bean
    public JpaTransactionManager transactionManager() throws ClassNotFoundException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws ClassNotFoundException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPackagesToScan("net.kadirderer.btc.db.model");
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        
        entityManagerFactoryBean.setDataSource(dataSource());

        return entityManagerFactoryBean;
    }
    
    // myBatis configuration
    @Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
    	SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		sqlSessionFactoryBean.setTypeAliasesPackage("net.kadirderer.btc.db.mybatis.domain");
		return sqlSessionFactoryBean.getObject();
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