package net.kadirderer.btc.web.dto;

public class SellOrderDto {
	
	private Double amount;
	private Double price;
	private boolean isAutoTrade;
	private boolean isAutoUpdate;
	private Integer platformId;
	private String username;
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public boolean isAutoTrade() {
		return isAutoTrade;
	}
	
	public void setAutoTrade(boolean isAutoTrade) {
		this.isAutoTrade = isAutoTrade;
	}
	
	public Integer getPlatformId() {
		return platformId;
	}
	
	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAutoUpdate() {
		return isAutoUpdate;
	}

	public void setAutoUpdate(boolean isAutoUpdate) {
		this.isAutoUpdate = isAutoUpdate;
	}
	
}
