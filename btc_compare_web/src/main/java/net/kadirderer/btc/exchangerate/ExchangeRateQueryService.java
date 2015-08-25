package net.kadirderer.btc.exchangerate;

public interface ExchangeRateQueryService {
	
	public ExchangeRatesTable getExchangeRateTable(String jsonString);
	
	public ExchangeRateQueryResult qetExchangeRates(ExchangeRateQuery obj, String jsonString);
	
	public String queryExchangeRateJSON();
	
	public ExchangeRatesTable changeBase(ExchangeRatesTable oldTable, String newBase);

}
