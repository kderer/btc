package net.kadirderer.btc.impl.util.btcc;

import net.kadirderer.btc.util.enumaration.ApiType;

public interface BtcChinaApiCallable {
	
	public String getApiName();
	
	public String getParameters(String... parameters);
	
	public String getJSONRequestParameters(String... parameters);
	
	public ApiType getApiType();

}
