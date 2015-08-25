package net.kadirderer.btc.api;

public enum OrderType {
	
	BUY("label.ordertype.buy", 'B'),
	SELL("label.ordertype.sell", 'S');
	
	private char code;
	private String i18nKey;
	
	private OrderType(String i18nKey, char code) {
		this.i18nKey = i18nKey;
		this.code = code;
	}
	
	public char getCode() {
		return this.code;
	}
	
	public String getI18NKey() {		
		return this.i18nKey;
	}
	
	public static String getI18NKey(char type) {		
		for(OrderType orderType : OrderType.values()) {
			if(type == orderType.getCode()) {
				return orderType.getI18NKey();
			}
		}		
		return null;
	}

}
