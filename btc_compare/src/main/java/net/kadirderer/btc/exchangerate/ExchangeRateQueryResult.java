package net.kadirderer.btc.exchangerate;

public class ExchangeRateQueryResult {
	
	private ExchangeRateQuery query;
	private Double rateByFirst;
	private Double rateBySecond;
	
	public ExchangeRateQuery getQuery() {
		return query;
	}
	public void setQuery(ExchangeRateQuery query) {
		this.query = query;
	}
	public Double getRateByFirst() {
		return rateByFirst;
	}
	public void setRateByFirst(Double rateByFirst) {
		this.rateByFirst = rateByFirst;
	}
	public Double getRateBySecond() {
		return rateBySecond;
	}
	public void setRateBySecond(Double rateBySecond) {
		this.rateBySecond = rateBySecond;
	}
	
	
	

}
