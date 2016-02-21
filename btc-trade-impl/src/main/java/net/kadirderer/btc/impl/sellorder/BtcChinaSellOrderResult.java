package net.kadirderer.btc.impl.sellorder;

import net.kadirderer.btc.api.sellorder.SellOrderResult;
import net.kadirderer.btc.impl.util.btcc.BtcChinaErrorResult;

public class BtcChinaSellOrderResult extends SellOrderResult {
	
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
