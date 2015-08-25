package net.kadirderer.btc.exchangerate;

public interface ExchangeRateQueryService {
	
	public ExchangeRatesTable queryExchangeRateTable();
	
	public ExchangeRateQueryResult queryRates(ExchangeRateQuery obj);
	
	public String queryExchangeRateJSON();
	
	public ExchangeRatesTable getExchangeRateTable(String jsonString);
	
	public ExchangeRateQueryResult qetExchangeRates(ExchangeRateQuery obj, String jsonString);

}
