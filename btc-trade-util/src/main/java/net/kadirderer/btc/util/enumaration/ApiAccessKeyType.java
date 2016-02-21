package net.kadirderer.btc.util.enumaration;

public enum ApiAccessKeyType {
	
	BTC_CHINA_ACCESS_KEY(null, 'A'),
	BTC_CHINA_SECRET_KEY(null, 'B');
	
	private char code;
	private String i18nKey;
	
	private ApiAccessKeyType(String i18nKey, char code) {
		this.i18nKey = i18nKey;
		this.code = code;
	}
	
	public char getCode() {
		return this.code;
	}
	
	public String getI18NKey() {		
		return this.i18nKey;
	}

}
