package net.kadirderer.btc.impl.orderbook;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.kadirderer.btc.api.orderbook.OrderbookResult;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BtceOrderbookResult implements OrderbookResult {
	
	private Map<String, Double> btc_usd;	

	@Override
	public Double getHighestAsk() {
		return btc_usd.get("buy");
	}

	@Override
	public Double getHighestBid() {
		return btc_usd.get("sell");
	}

	public Map<String, Double> getBtc_usd() {
		return btc_usd;
	}

	public void setBtc_usd(Map<String, Double> btc_usd) {
		this.btc_usd = btc_usd;
	}
}
