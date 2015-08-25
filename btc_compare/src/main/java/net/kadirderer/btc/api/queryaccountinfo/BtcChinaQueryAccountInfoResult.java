package net.kadirderer.btc.api.queryaccountinfo;

import java.util.LinkedHashMap;
import java.util.Map;

import net.kadirderer.btc.api.util.btcchina.BtcChinaErrorResult;

public class BtcChinaQueryAccountInfoResult extends QueryAccountInfoResult {
	
	private BtcChinaErrorResult error;
	private Map<String, Object> result;
	private String id;
	
	public BtcChinaErrorResult getError() {
		return error;
	}
	
	public void setError(BtcChinaErrorResult error) {
		this.error = error;
	}
	
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@Override
	@SuppressWarnings("unchecked")
	public double getBtcBalance() {		
		String amount = (String)((LinkedHashMap<String, Object>)getBalance().get("btc")).get("amount");
		return Double.valueOf(amount);
	}

	@Override
	@SuppressWarnings("unchecked")
	public double getCurrencyBalance() {
		String amount = (String)((LinkedHashMap<String, Object>)getBalance().get("cny")).get("amount");
		return Double.valueOf(amount);
	}
	
	@SuppressWarnings("unchecked")
	private LinkedHashMap<String, Object> getBalance() {
		if(result != null) {
			return (LinkedHashMap<String, Object>) result.get("balance");
		}
		
		return null;
	}	

}
