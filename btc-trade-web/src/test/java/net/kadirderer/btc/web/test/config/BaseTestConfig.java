package net.kadirderer.btc.web.test.config;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.kadirderer.btc.config.TradeApiConfig;
import net.kadirderer.btc.test.config.DatabaseTestConfig;
import net.kadirderer.exchangerate.config.ExchangeRateConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DatabaseTestConfig.class, TradeApiConfig.class, ApplicationTestConfig.class,
		ExchangeRateConfig.class })
public abstract class BaseTestConfig {

}
