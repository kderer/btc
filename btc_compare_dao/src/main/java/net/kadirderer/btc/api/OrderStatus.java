package net.kadirderer.btc.api;


public enum OrderStatus {
	
	PENDING("label.orderstatus.pending", 'P'),
	DONE("label.orderstatus.done", 'D'),
	CANCELLED("label.orderstatus.cancelled", 'C'),
	FAILED("label.orderstatus.failed", 'F'),
	CANCEL_FAILED("label.orderstatus.cancel.failed", 'R');
	
	private char code;
	private String i18nKey;
	
	private OrderStatus(String i18nKey, char code) {
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
		for(OrderStatus orderStatus : OrderStatus.values()) {
			if(status == orderStatus.getCode()) {
				return orderStatus.getI18NKey();
			}
		}		
		return null;
	}
	
}
