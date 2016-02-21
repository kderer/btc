package net.kadirderer.btc.db.model;

import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(name = "DailyProfitReport",
	columns = { @ColumnResult(name = "date"),
				@ColumnResult(name = "status"),
				@ColumnResult(name = "amount"),
				@ColumnResult(name = "profit")
})
public class DailyReportDetail {
	
	@Id
	private String date;
	private char status;
	private int amount;
	private double totalProfit;
	
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

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
}
