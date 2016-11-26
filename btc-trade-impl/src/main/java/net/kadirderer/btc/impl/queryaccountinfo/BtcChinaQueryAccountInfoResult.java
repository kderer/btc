package net.kadirderer.btc.impl.queryaccountinfo;

import java.util.LinkedHashMap;
import java.util.Map;

import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoResult;
import net.kadirderer.btc.impl.util.NumberUtil;
import net.kadirderer.btc.impl.util.btcc.BtcChinaErrorResult;

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
		return NumberUtil.format(Double.parseDouble(amount));
	}

	@Override
	@SuppressWarnings("unchecked")
	public double getCurrencyBalance() {
		String amount = (String)((LinkedHashMap<String, Object>)getBalance().get("cny")).get("amount");
		return NumberUtil.format(Double.parseDouble(amount));
	}
	
	@SuppressWarnings("unchecked")
	private LinkedHashMap<String, Object> getBalance() {
		if(result != null) {
			return (LinkedHashMap<String, Object>) result.get("balance");
		}
		
		return null;
	}	

}
