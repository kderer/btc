package net.kadirderer.btc.api.orderbook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BtcChinaOrderbookResult implements OrderbookResult {
	
	private Double[][] asks;
	private Double[][] bids;
	
	public Double[][] getAsks() {
		return asks;
	}
	public void setAsks(Double[][] asks) {
		this.asks = asks;
	}
	public Double[][] getBids() {
		return bids;
	}
	public void setBids(Double[][] bids) {
		this.bids = bids;
	}
	
	@Override
	public Double getHighestAsk() {
		return asks[0][0];
	}
	@Override
	public Double getHighestBid() {
		// TODO Auto-generated method stub
		return bids[0][0];
	}
	
	
}
