package net.kadirderer.btc.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.kadirderer.btc.db.util.DateUtil;
import net.kadirderer.btc.util.enumaration.OrderStatus;
import net.kadirderer.btc.util.enumaration.OrderType;

@Entity
@Table(name = "T_USERORDER")
public class UserOrder {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "PLATFORMID")
	private Integer platformId;
	
	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "ORDERTYPE")
	private char orderType;
	
	@Column(name = "PARENTID")
	private Integer parentId;
	
	@Column(name = "BASEPRICE")
	private double basePrice;
	
	@Column(name = "PRICE")
	private double price;
	
	@Column(name = "AMOUNT")
	private double amount;
	
	@Column(name = "COMPLETEDAMOUNT")
	private double completedAmount;	
	
	@Column(name = "RETURNID")
	private String returnId;
	
	@Column(name = "STATUS")
	private char status;
	
	@Column(name = "CREATEDATE")
	private Date createDate;
	
	@Column(name = "UPDATEDATE")
	private Date updateDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public char getOrderType() {
		return orderType;
	}

	public void setOrderType(char orderType) {
		this.orderType = orderType;
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

	public String getReturnId() {
		return returnId;
	}

	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public double getCompletedAmount() {
		return completedAmount;
	}

	public void setCompletedAmount(double completedAmount) {
		this.completedAmount = completedAmount;
	}
	
	public String getStatusKey() {
		return OrderStatus.getI18NKey(status);
	}
	
	public String getOrderTypeKey() {
		return OrderType.getI18NKey(orderType);
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}	
	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getCreateDateStr() {
		return DateUtil.format(this.createDate);
	}

	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}
	
}
