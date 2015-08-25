package net.kadirderer.btc.api.queryorder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class QueryOrderResult {
	
	public abstract QueryOrderStatus getStatus();
	
	public abstract double getAmount();
	
	public abstract double getOriginalAmount();
	
	public abstract double getCompletedAmount();
	
	public abstract String getType();
	
	public abstract double getPrice();
}
