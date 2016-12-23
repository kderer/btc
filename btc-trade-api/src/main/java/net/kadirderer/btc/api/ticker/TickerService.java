package net.kadirderer.btc.api.ticker;

public interface TickerService {
	
	public TickerResult getTicker(Integer platformId) throws Exception;
	
}
