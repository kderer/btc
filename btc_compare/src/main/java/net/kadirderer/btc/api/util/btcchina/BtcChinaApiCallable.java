package net.kadirderer.btc.api.util.btcchina;

import net.kadirderer.btc.api.ApiType;

public interface BtcChinaApiCallable {
	
	public String getApiName();
	
	public String getParameters(String... parameters);
	
	public String getJSONRequestParameters(String... parameters);
	
	public ApiType getApiType();

}
