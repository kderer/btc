package net.kadirderer.btc.service;

import java.util.Map;

public interface ConfigService {
	
	public void setConfig(String configName, String configValue);
	
	public Map<String, String> receiveConfigValues();

}
