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
	
	@Column(name = "CHECKDELTA")
	private Double checkDelta;
	
	@Column(name = "HLGD")
	private Double highestLastGmobDiff;
	
	@Column(name = "HGPD")
	private Double highestGmobPriceDiff;	
	
	@Column(name = "LOWESTASK")
	private Double lowestAsk;
	
	@Column(name = "GMOA")
	private Double gmoa;		
	
	@Column(name = "HIGHESTBID")
	private Double highestBid;
	
	@Column(name = "GMOB")
	private Double gmob;
	
	@Column(name = "HGMOB")
	private Double highestGmob;
	
	@Column(name = "LHGMOB")
	private Double lastHighestGmob;
	
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

	public Double getCheckDelta() {
		return checkDelta;
	}

	public void setCheckDelta(Double checkDelta) {
		this.checkDelta = checkDelta;
	}

	public Double getHighestLastGmobDiff() {
		return highestLastGmobDiff;
	}

	public void setHighestLastGmobDiff(Double highestLastGmobDiff) {
		this.highestLastGmobDiff = highestLastGmobDiff;
	}

	public Double getHighestGmobPriceDiff() {
		return highestGmobPriceDiff;
	}

	public void setHighestGmobPriceDiff(Double highestGmobPriceDiff) {
		this.highestGmobPriceDiff = highestGmobPriceDiff;
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

	public Double getHighestGmob() {
		return highestGmob;
	}

	public void setHighestGmob(Double highestGmob) {
		this.highestGmob = highestGmob;
	}

	public Double getLastHighestGmob() {
		return lastHighestGmob;
	}

	public void setLastHighestGmob(Double lastHighestGmob) {
		this.lastHighestGmob = lastHighestGmob;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getFormattedTime() {
		if (formattedTime == null) {
			formattedTime = sdf.format(createTime);
		}
		
		return formattedTime;
	}
}
