package net.kadirderer.btc.test.dao;

import static org.junit.Assert.assertNotNull;
import net.kadirderer.btc.db.dao.ExchangeRatesDao;
import net.kadirderer.btc.db.model.ExchangeRates;
import net.kadirderer.btc.db.model.RecordGroup;
import net.kadirderer.btc.test.config.DatabaseTestConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class})
public class ExchangeRatesDaoTest {

	@Autowired
	private ExchangeRatesDao exchangeRatesDao;	
	
	@Test
	public void testInsert() {
		
		ExchangeRates exchangeRates = new ExchangeRates();
		RecordGroup group = new RecordGroup();
		group.setRecordGroupId(123123);
		
		String ratesMap = "asdasdas";
		
		exchangeRates.setRecordGroup(group);
		exchangeRates.setRatesMap(ratesMap);
		
		exchangeRatesDao.insert(exchangeRates);
		
		assertNotNull(exchangeRates.getId());
	}


}
