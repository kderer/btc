package net.kadirderer.btc.api.queryaccountinfo;

public abstract class QueryAccountInfoResult {
	
	private String platformName;
	
	public abstract double getBtcBalance();
	
	public abstract double getCurrencyBalance();

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
}
