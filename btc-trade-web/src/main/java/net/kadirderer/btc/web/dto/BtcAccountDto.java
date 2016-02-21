package net.kadirderer.btc.web.dto;

public class BtcAccountDto {
	
	private Double currencyBalance;
	private Double btcBalance;
	
	public Double getCurrencyBalance() {
		return currencyBalance;
	}
	
	public void setCurrencyBalance(Double currencyBalance) {
		this.currencyBalance = currencyBalance;
	}
	
	public Double getBtcBalance() {
		return btcBalance;
	}
	
	public void setBtcBalance(Double btcBalance) {
		this.btcBalance = btcBalance;
	}	

}
