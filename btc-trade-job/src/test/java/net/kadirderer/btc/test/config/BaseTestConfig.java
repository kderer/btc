package net.kadirderer.btc.test.config;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.kadirderer.btc.util.test.config.UtilTestConfig;
import net.kadirderer.exchangerate.test.ExchangeRateTestConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TradeApiTestConfig.class, ApplicationTestConfig.class, 
		UtilTestConfig.class, ExchangeRateTestConfig.class, DatabaseTestConfig.class})
public abstract class BaseTestConfig {

}
