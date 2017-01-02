package net.kadirderer.btc.db.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "T_STATISTICS")
public class Statistics {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;	
	
	@Column(name = "LOWESTASK")
	private Double lowestAsk;
	
	@Column(name = "GMOA")
	private Double gmoa;		
	
	@Column(name = "HIGHESTBID")
	private Double highestBid;
	
	@Column(name = "GMOB")
	private Double gmob;
	
	@Column(name = "DAILY_HIGH")
	private Double dailyHigh;
	
	@Column(name = "DAILY_LOW")
	private Double dailyLow;
	
	@Column(name = "CREATETIME")
	private Date createTime;
	
	@Transient
	private String formattedTime;
	
	@Transient
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Double getLowestAsk() {
		return lowestAsk;
	}

	public void setLowestAsk(Double lowestAsk) {
		this.lowestAsk = lowestAsk;
	}

	public Double getGmoa() {
		return gmoa;
	}

	public void setGmoa(Double gmoa) {
		this.gmoa = gmoa;
	}

	public Double getHighestBid() {
		return highestBid;
	}

	public void setHighestBid(Double highestBid) {
		this.highestBid = highestBid;
	}

	public Double getGmob() {
		return gmob;
	}

	public void setGmob(Double gmob) {
		this.gmob = gmob;
	}	

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Double getDailyHigh() {
		return dailyHigh;
	}

	public void setDailyHigh(Double dailyHigh) {
		this.dailyHigh = dailyHigh;
	}
	
	public Double getDailyLow() {
		return dailyLow;
	}

	public void setDailyLow(Double dailyLow) {
		this.dailyLow = dailyLow;
	}

	public String getFormattedTime() {
		if (formattedTime == null) {
			formattedTime = sdf.format(createTime);
		}
		
		return formattedTime;
	}	
}
