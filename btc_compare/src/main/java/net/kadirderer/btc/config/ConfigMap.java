package net.kadirderer.btc.config;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class ConfigMap {
	
	private static final String RECORDGROUPJOB_ENABLED = "recordgroupjob_enabled";
	private static final String AUTOTRADE_ENABLED = "autotrade_enabled";
	private static final String SELLORDER_CHECK_PERIOD = "sellorder_check_period";
	private static final String SELLORDER_DELTA = "sellorder_delta";
	private static final String SELLREORDER_DELTA = "sellreorder_delta";
	private static final String SELLORDER_TIME_LIMIT = "sellorder_time_limit";
	private static final String BUYORDER_CHECK_PERIOD = "buyorder_check_period";
	private static final String BUYORDER_DELTA = "buyorder_delta";
	private static final String BUYREORDER_DELTA = "buyreorder_delta";
	private static final String BUYORDER_TIME_LIMIT = "buyorder_time_limit";
	
	private static Map<String, String> configMap; 
	
	static {
		configMap = new HashMap<String, String>();
		
		configMap.put(RECORDGROUPJOB_ENABLED, "true");
		configMap.put(AUTOTRADE_ENABLED, "true");
		configMap.put(SELLORDER_CHECK_PERIOD, "120");
		configMap.put(SELLORDER_DELTA, "0.2");
		configMap.put(SELLREORDER_DELTA, "0.25");
		configMap.put(SELLORDER_TIME_LIMIT, "1800");
		configMap.put(BUYORDER_CHECK_PERIOD, "20");
		configMap.put(BUYORDER_DELTA, "0.5");
		configMap.put(BUYORDER_TIME_LIMIT, "180");
	}
	
	
	public static boolean isRecordGroupJobEnabled() {
		return "true".equals(configMap.get(RECORDGROUPJOB_ENABLED));
	}
	
	public static void setRecordGroupJobEnabled(String value) {
		configMap.put(RECORDGROUPJOB_ENABLED, value);
	}
	
	public static boolean isAutoTradeEnabled() {
		return "true".equals(configMap.get(AUTOTRADE_ENABLED));
	}
	
	public static void setAutoTradeEnabled(String value) {
		configMap.put(AUTOTRADE_ENABLED, value);
	}
	
	public static int sellOrderCheckPeriod() {
		return Integer.parseInt(configMap.get(SELLORDER_CHECK_PERIOD));
	}
	
	public static void setSellOrderCheckPeriod(String value) {
		configMap.put(SELLORDER_CHECK_PERIOD, value);
	}
	
	public static Double sellOrderDelta() {
		return Double.parseDouble(configMap.get(SELLORDER_DELTA));
	}
	
	public static void setSellOrderDelta(String value) {
		configMap.put(SELLORDER_DELTA, value);
	}
	
	public static Double sellReOrderDelta() {
		return Double.parseDouble(configMap.get(SELLREORDER_DELTA));
	}
	
	public static void setSellReOrderDelta(String delta) {
		configMap.put(SELLREORDER_DELTA, delta);
	}
	
	public static int sellOrderTimeLimit() {
		return Integer.parseInt(configMap.get(SELLORDER_TIME_LIMIT));
	}
	
	public static void setSellTimeLimit(String value) {
		configMap.put(SELLORDER_TIME_LIMIT, value);
	}
	
	public static int buyOrderCheckPeriod() {
		return Integer.parseInt(configMap.get(BUYORDER_CHECK_PERIOD));
	}
	
	public static void setBuyOrderCheckPeriod(String value) {
		configMap.put(BUYORDER_CHECK_PERIOD, value);
	}
	
	public static Double buyOrderDelta() {
		return Double.parseDouble(configMap.get(BUYORDER_DELTA));
	}
	
	public static void setBuyOrderDelta(String value) {
		configMap.put(BUYORDER_DELTA, value);
	}
	
	public static Double buyReOrderDelta() {
		return Double.parseDouble(configMap.get(BUYREORDER_DELTA));
	}
	
	public static void setBuyReOrderDelta(String value) {
		configMap.put(BUYREORDER_DELTA, value);
	}
	
	public static int buyOrderTimeLimit() {
		return Integer.parseInt(configMap.get(BUYORDER_TIME_LIMIT));
	}
	
	public static void setBuyTimeLimit(String value) {
		configMap.put(BUYORDER_TIME_LIMIT, value);
	}
	
	public static void setKeyValue(String key, String value) {
		configMap.put(key, value);
	}
	
	
	
	public static String valueMapJson() {
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(configMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
