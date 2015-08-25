package net.kadirderer.btc.exchangerate;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRatesTable {
	
	private String base;
	private String date;
	private HashMap<String, Double> rates;
	
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public HashMap<String, Double> getRates() {
		return rates;
	}
	public void setRates(HashMap<String, Double> rates) {
		this.rates = rates;
	}
	
	
	

}
