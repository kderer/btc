package net.kadirderer.btc.db.mybatis.domain;

import org.apache.ibatis.type.Alias;

import net.kadirderer.btc.util.NumberDisplayUtil;

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
	
	public String getSellProfitStr() {
		return NumberDisplayUtil.dailyReportFormat(sellProfit);
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
	
	public String getBuyProfitStr() {
		return NumberDisplayUtil.dailyReportFormat(buyProfit);
	}

	public void setBuyProfit(double buyProfit) {
		this.buyProfit = buyProfit;
	}

	public double getTotalProfit() {
		return totalProfit;
	}
	
	public String getTotalProfitStr() {
		return NumberDisplayUtil.dailyReportFormat(totalProfit);
	}

	public void setTotalProfit(double totalProfit) {
		this.totalProfit = totalProfit;
	}
	
}
