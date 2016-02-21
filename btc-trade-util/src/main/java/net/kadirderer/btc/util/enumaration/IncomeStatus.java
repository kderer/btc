package net.kadirderer.btc.util.enumaration;


public enum IncomeStatus {
	
	PENDING("label.orderstatus.pending", 'P'),
	USED("label.orderstatus.done", 'U');
	
	private char code;
	private String i18nKey;
	
	private IncomeStatus(String i18nKey, char code) {
		this.i18nKey = i18nKey;
		this.code = code;
	}
	
	public char getCode() {
		return this.code;
	}
	
	public String getI18NKey() {		
		return this.i18nKey;
	}
	
	public static String getI18NKey(char status) {		
		for(IncomeStatus orderStatus : IncomeStatus.values()) {
			if(status == orderStatus.getCode()) {
				return orderStatus.getI18NKey();
			}
		}		
		return null;
	}
	
}
