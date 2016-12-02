package net.kadirderer.btc.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "T_FAILEDORDER")
public class FailedOrder {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "USERORDERID")
	private int userOrderId;
	
	@Column(name = "MESSAGE")
	private String message;
	
	@Column(name = "ERROR_CODE")
	private String errorCode;
	
	@Column(name = "RETRY_RESULT")
	private Character retryResult;
	
	@Column(name = "CREATEDATE")
	private Date createDate;
	
	@Transient
	private UserOrder userOrder;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getUserOrderId() {
		return userOrderId;
	}

	public void setUserOrderId(int userOrderId) {
		this.userOrderId = userOrderId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Character getRetryResult() {
		return retryResult;
	}

	public void setRetryResult(Character retryResult) {
		this.retryResult = retryResult;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public UserOrder getUserOrder() {
		return userOrder;
	}

	public void setUserOrder(UserOrder userOrder) {
		this.userOrder = userOrder;
	}
	
	public Character getOrderType() {
		if (userOrder != null) {
			return userOrder.getOrderType();
		}
		return null;
	}
	
	public Double getAmount() {
		if (userOrder != null) {
			return userOrder.getAmount();
		}
		return null;
	}
	
	public Double getPrice() {
		if (userOrder != null) {
			return userOrder.getPrice();
		}
		return null;
	}
	
}
