package net.kadirderer.btc.api.marketdepth;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import net.kadirderer.btc.api.util.btcchina.BtcChinaErrorResult;

public class BtcChinaMarketDepthResult extends MarketDepthResult {
	
	private String id;
	private BtcChinaErrorResult error;
	private LinkedHashMap<String, LinkedHashMap<String, Object>> result;
	
	@SuppressWarnings("unchecked")
	@Override
	public double getPriceOfMaxBidAmount() {		
		double price = 0;
		if(error == null) {
			ArrayList<LinkedHashMap<String, Object>> bidList = (ArrayList<LinkedHashMap<String, Object>>) 
					result.get("market_depth").get("bid");
			double tempAmount = 0;			

			for(LinkedHashMap<String, Object> depthMap : bidList) {
				double amount = Double.valueOf(depthMap.get("amount").toString());
				
				if(amount > tempAmount) {
					price = Double.valueOf(depthMap.get("price").toString());
				}
			}
			
		}
		return price;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public double getPriceOfMaxAskAmount() {
		double price = 0;
		if(error == null) {
			ArrayList<LinkedHashMap<String, Object>> bidList = (ArrayList<LinkedHashMap<String, Object>>) 
					result.get("market_depth").get("ask");
			double tempAmount = 0;			
			for(LinkedHashMap<String, Object> depthMap : bidList) {
				double amount = Double.valueOf(depthMap.get("amount").toString());
				
				if(amount > tempAmount) {
					price = Double.valueOf(depthMap.get("price").toString());
				}
			}
			
		}
		return price;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public double getHighestBid() {
		double price = 0;
		if(error == null) {
			ArrayList<LinkedHashMap<String, Object>> bidList = (ArrayList<LinkedHashMap<String, Object>>) 
					result.get("market_depth").get("bid");
			
			LinkedHashMap<String, Object> firstMap = bidList.get(0);
			price = Double.valueOf(firstMap.get("price").toString());
			
		}
		return price;
	}

	@SuppressWarnings("unchecked")
	@Override
	public double getLowestAsk() {
		double price = 0;
		if(error == null) {
			ArrayList<LinkedHashMap<String, Object>> bidList = (ArrayList<LinkedHashMap<String, Object>>) 
					result.get("market_depth").get("ask");
			
			LinkedHashMap<String, Object> firstMap = bidList.get(0);
			price = Double.valueOf(firstMap.get("price").toString());			
		}
		return price;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public BtcChinaErrorResult getError() {
		return error;
	}
	
	public void setError(BtcChinaErrorResult error) {
		this.error = error;
	}

	public LinkedHashMap<String, LinkedHashMap<String, Object>> getResult() {
		return result;
	}

	public void setResult(
			LinkedHashMap<String, LinkedHashMap<String, Object>> result) {
		this.result = result;
	}
	
}
