package net.kadirderer.btc.api.ticker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class TickerResult {
	
	public abstract double get24HoursHigh();
	
	public abstract double get24HoursLow();

}
