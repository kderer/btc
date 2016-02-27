package net.kadirderer.btc.db.model;

import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(name = "DailyProfitReport",
	columns = { @ColumnResult(name = "date"),
				@ColumnResult(name = "orderType"),
				@ColumnResult(name = "amount"),
				@ColumnResult(name = "profit")
})
public class DailyReportDetail {
	
	@Id
	private String date;
	private int amount;
	private double totalProfit;
	private char orderType;
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public double getTotalProfit() {
		return totalProfit;
	}
	
	public void setTotalProfit(double totalProfit) {
		this.totalProfit = totalProfit;
	}

	public char getOrderType() {
		return orderType;
	}

	public void setOrderType(char orderType) {
		this.orderType = orderType;
	}
	
}
