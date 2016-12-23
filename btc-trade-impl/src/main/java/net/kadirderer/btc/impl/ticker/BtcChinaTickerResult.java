package net.kadirderer.btc.impl.ticker;

import java.util.LinkedHashMap;

import net.kadirderer.btc.api.ticker.TickerResult;
import net.kadirderer.btc.impl.util.NumberUtil;
import net.kadirderer.btc.impl.util.btcc.BtcChinaErrorResult;

public class BtcChinaTickerResult extends TickerResult {
	
	private String id;
	private BtcChinaErrorResult error;
	private LinkedHashMap<String, Object> ticker_btccny;
	
	@Override
	public double get24HoursHigh() {
		if(error == null) {
			return NumberUtil.parse(ticker_btccny.get("high").toString());
		}
		
		return 0;
	}
	
	@Override
	public double get24HoursLow() {
		if(error == null) {
			return NumberUtil.parse(ticker_btccny.get("low").toString());
		}
		return 0;
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

	public LinkedHashMap<String, Object> getTicker_btccny() {
		return ticker_btccny;
	}

	public void setTicker_btccny(LinkedHashMap<String, Object> ticker_btccny) {
		this.ticker_btccny = ticker_btccny;
	}	
}
