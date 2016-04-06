package net.kadirderer.btc.db.mybatis.domain;

import org.apache.ibatis.type.Alias;

@Alias("DailyReportDetail")
public class DailyProfitDetail {
	
	private String date;
	private int sellAmount;
	private double sellProfit;
	private int buyAmount;
	private double buyProfit;
	private double totalProfit;
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public int getSellAmount() {
		return sellAmount;
	}

	public void setSellAmount(int sellAmount) {
		this.sellAmount = sellAmount;
	}

	public double getSellProfit() {
		return sellProfit;
	}

	public void setSellProfit(double sellProfit) {
		this.sellProfit = sellProfit;
	}

	public int getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(int buyAmount) {
		this.buyAmount = buyAmount;
	}

	public double getBuyProfit() {
		return buyProfit;
	}

	public void setBuyProfit(double buyProfit) {
		this.buyProfit = buyProfit;
	}

	public double getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(double totalProfit) {
		this.totalProfit = totalProfit;
	}
	
}
