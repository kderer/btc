package net.kadirderer.btc.api.queryorder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.kadirderer.btc.db.model.UserOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class QueryOrderResult {
	
	public abstract QueryOrderStatus getStatus();
	
	public abstract double getAmount();
	
	public abstract double getOriginalAmount();
	
	public abstract double getCompletedAmount();
	
	public abstract double getLastCompletedAmount();
	
	public abstract String getType();
	
	public abstract double getPrice();
	
	public abstract UserOrder getUserOrder();
}
