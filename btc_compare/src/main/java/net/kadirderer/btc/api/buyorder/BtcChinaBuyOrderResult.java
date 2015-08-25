package net.kadirderer.btc.api.buyorder;

import net.kadirderer.btc.api.util.btcchina.BtcChinaErrorResult;

public class BtcChinaBuyOrderResult extends BuyOrderResult {
	
	private String result;	
	private String id;
	private BtcChinaErrorResult error;	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
