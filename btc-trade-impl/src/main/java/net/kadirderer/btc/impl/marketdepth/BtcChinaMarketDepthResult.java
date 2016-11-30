package net.kadirderer.btc.impl.marketdepth;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import net.kadirderer.btc.api.marketdepth.MarketDepthResult;
import net.kadirderer.btc.impl.util.btcc.BtcChinaErrorResult;

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

	@SuppressWarnings("unchecked")
	@Override
	public double getGeometricMeanOfBids() {
		double product = 1.0;
		int bidListSize = 1;
		if(error == null) {
			ArrayList<LinkedHashMap<String, Object>> bidList = (ArrayList<LinkedHashMap<String, Object>>) 
					result.get("market_depth").get("bid");
			double bid = 1.0;
			for(LinkedHashMap<String, Object> depthMap : bidList) {
				bid = Double.valueOf(depthMap.get("price").toString());				
				product *= bid;
			}
			
			bidListSize = bidList.size();
		}
		return Math.pow(product, 1.0 / bidListSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public double[] getMaxAndGeometricMean() {
		double[] maxAndGMArray = new double[6];
		double product = 1.0;
		double poa = 1.0;
		double price = 0;
		
		if(error == null) {
			ArrayList<LinkedHashMap<String, Object>> list = (ArrayList<LinkedHashMap<String, Object>>) 
					result.get("market_depth").get("ask");
			
			LinkedHashMap<String, Object> firstMap = list.get(0);
			price = Double.valueOf(firstMap.get("price").toString());
			
			for(LinkedHashMap<String, Object> depthMap : list) {
				product *= Double.valueOf(depthMap.get("price").toString());
				poa *= Double.valueOf(depthMap.get("amount").toString());
			}
			
			maxAndGMArray[0] = price;
			maxAndGMArray[1] = Math.pow(product, 1.0 / list.size());
			maxAndGMArray[4] = Math.pow(poa, 1.0 / list.size());
			
			list = (ArrayList<LinkedHashMap<String, Object>>)result.get("market_depth").get("bid");			
			price = Double.valueOf(list.get(0).get("price").toString());
			
			product = 1.0;
			poa = 1.0;
			for(LinkedHashMap<String, Object> depthMap : list) {
				product *= Double.valueOf(depthMap.get("price").toString());
			}
			
			maxAndGMArray[2] = price;
			maxAndGMArray[3] = Math.pow(product, 1.0 / list.size());
			maxAndGMArray[5] = Math.pow(poa, 1.0 / list.size());
		}
		
		return maxAndGMArray;
	}
	
}
