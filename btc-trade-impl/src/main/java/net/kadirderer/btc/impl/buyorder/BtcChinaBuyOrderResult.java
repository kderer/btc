package net.kadirderer.btc.impl.buyorder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.kadirderer.btc.api.buyorder.BuyOrderResult;
import net.kadirderer.btc.impl.util.btcc.BtcChinaErrorResult;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BtcChinaBuyOrderResult extends BuyOrderResult {
	
	private String result;
	private BtcChinaErrorResult error;	

	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}

	public BtcChinaErrorResult getError() {
		return error;
	}

	public void setError(BtcChinaErrorResult error) {
		this.error = error;
	}
}
