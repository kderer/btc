package net.kadirderer.btc.web.dto;

public class UpdateOrderDto {
	
	private int id;
	private String returnId;
	private char orderType;
	private double currentPrice;
	private double price;
	private double amount;	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getReturnId() {
		return returnId;
	}
	
	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}
	
	public char getOrderType() {
		return orderType;
	}

	public void setOrderType(char orderType) {
		this.orderType = orderType;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}
	
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}

}
