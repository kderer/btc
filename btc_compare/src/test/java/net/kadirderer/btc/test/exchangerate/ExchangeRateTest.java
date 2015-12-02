package net.kadirderer.btc.test.exchangerate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.kadirderer.btc.exchangerate.ExchangeRateQuery;
import net.kadirderer.btc.exchangerate.ExchangeRateQueryResult;
import net.kadirderer.btc.exchangerate.ExchangeRateQueryService;
import net.kadirderer.btc.exchangerate.ExchangeRatesTable;
import net.kadirderer.btc.test.config.TestConfig;

public class ExchangeRateTest extends TestConfig {
	
	private ExchangeRateQueryService erqs;
	
	
	@Override
	public void getObjectFromContext(AnnotationConfigApplicationContext context) {
		erqs = context.getBean(ExchangeRateQueryService.class);
	}
	
	
	@Test
	public void queryTest() {
		ExchangeRatesTable table = erqs.queryExchangeRateTable();
		
		assertNotNull(table.getRates().get("TRY"));
	}
	
	@Test
	public void queryExchangeRateTest() {
		
		ExchangeRateQuery query = new ExchangeRateQuery();
		query.setFirstCurrency("TRY");
		query.setSecondCurrency("CNY");
		
		ExchangeRateQueryResult result = erqs.queryRates(query);
		
		assertEquals(2.65, result.getRateByFirst(), 0.05);
		
	}
	
	@Test
	public void testQueryRateString() {
		String result = erqs.queryExchangeRateJSON();
		
		assertNotEquals(250, result.length(), 50);
	}
	
	
	@Test
	public void testGetRateMapFromString() {
		String result = erqs.queryExchangeRateJSON();
		ExchangeRatesTable table = erqs.getExchangeRateTable(result);
		assertEquals(31, table.getRates().entrySet().size());
	}
	
	
	@Test
	public void qetExchangeRateTest() {
		String json = erqs.queryExchangeRateJSON();
		
		ExchangeRateQuery query = new ExchangeRateQuery();
		query.setFirstCurrency("TRY");
		query.setSecondCurrency("CNY");
		
		ExchangeRateQueryResult result = erqs.qetExchangeRates(query, json);
		
		assertEquals(2.65, result.getRateByFirst(), 0.05);
		
	}

}
