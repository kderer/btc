package net.kadirderer.btc.api.marketdepth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class MarketDepthResult {
	
	public abstract double getPriceOfMaxBidAmount();
	public abstract double getPriceOfMaxAskAmount();
	public abstract double getGeometricMeanOfBids();
	public abstract double getHighestBid();
	public abstract double getLowestAsk();
	public abstract double[] getMaxAndGeometricMean();

}
