package net.kadirderer.btc.config;

import net.kadirderer.btc.security.BtcAuthenticationProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"net.kadirderer.btc.security"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BtcAuthenticationProvider authProvider;	

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/assets/**").permitAll()
				.antMatchers("/index.html").permitAll()
				.antMatchers("/queryRecordGroup.request").permitAll()
				.antMatchers("/exchangeRates.html").permitAll()
				.antMatchers("/exchangeRates/**").permitAll()
				.anyRequest()
				.authenticated().and().formLogin().loginPage("/login.html")
				.permitAll().and()
				.logout().deleteCookies("remove")
				.invalidateHttpSession(true).logoutUrl("/logout.html")
				.logoutSuccessUrl("/index.html").permitAll();
	}
	
	
	/*
	 * This part is an example of getting datasource programmatically
	@Autowired
	private LocalContainerEntityManagerFactoryBean entityManagerFactory;
	
	@Autowired
	public DataSource dataSource;

	@Bean(name = "dataSource")
	public DataSource dataSource() throws ClassNotFoundException {
		return ((DatasourceConnectionProviderImpl) ((SessionFactoryImpl) ((HibernateEntityManagerFactory) entityManagerFactory
				.getNativeEntityManagerFactory()).getSessionFactory())
				.getConnectionProvider()).getDataSource();
	}
	*/

}
