package net.kadirderer.btc.impl.orderbook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.kadirderer.btc.api.orderbook.OrderbookResult;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BtcTurkOrderbookResult implements OrderbookResult {
	
	private String[][] asks;
	private String[][] bids;
	
	public String[][] getAsks() {
		return asks;
	}
	public void setAsks(String[][] asks) {
		this.asks = asks;
	}
	public String[][] getBids() {
		return bids;
	}
	public void setBids(String[][] bids) {
		this.bids = bids;
	}
	
	@Override
	public Double getHighestAsk() {
		return Double.valueOf(asks[0][0]);
	}
	@Override
	public Double getHighestBid() {
		return Double.valueOf(bids[0][0]);
	}
	
	
}
