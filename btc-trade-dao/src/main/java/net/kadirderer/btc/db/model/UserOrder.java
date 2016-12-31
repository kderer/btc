package net.kadirderer.btc.db.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.kadirderer.btc.db.util.DateUtil;
import net.kadirderer.btc.util.NumberDisplayUtil;
import net.kadirderer.btc.util.StringUtil;
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
	
	@Column(name = "IS_AUTO_TRADE", columnDefinition = "TINYINT(1)")
	private boolean autoTrade;
	
	@Column(name = "IS_AUTO_UPDATE", columnDefinition = "TINYINT(1)")
	private boolean autoUpdate;
	
	@Column(name = "BASEPRICE")
	private double basePrice;
	
	@Column(name = "PRICE")
	private double price;
	
	@Column(name = "TARGET")
	private double target;
	
	@Column(name = "AMOUNT")
	private double amount;
	
	@Column(name = "COMPLETEDAMOUNT")
	private double completedAmount;
	
	@Column(name = "HIGHEST_GMOB")
	private Double highestGmob;	
	
	@Column(name = "LAST_GMOB_ARRAY")
	private String lastGmobArray;
	
	@Column(name = "LAST_GMOA_ARRAY")
	private String lastGmoaArray;	

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
	
	public String getUpdateDateStr() {
		return DateUtil.format(this.updateDate);
	}

	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}

	public boolean isAutoTrade() {
		return autoTrade;
	}

	public void setAutoTrade(boolean autoTrade) {
		this.autoTrade = autoTrade;
	}

	public boolean isAutoUpdate() {
		return autoUpdate;
	}

	public void setAutoUpdate(boolean autoUpdate) {
		this.autoUpdate = autoUpdate;
	}
	
	public String getLastGmobArray() {
		return lastGmobArray;
	}

	public void setLastGmobArray(String lastGmoaArray) {
		this.lastGmobArray = lastGmoaArray;
	}
	
	public String getLastGmoaArray() {
		return lastGmoaArray;
	}

	public void setLastGmoaArray(String lastGmoaArray) {
		this.lastGmoaArray = lastGmoaArray;
	}
	
	public Double getHighestGmob() {
		return highestGmob;
	}

	public void setHighestGmob(Double highestGmob) {
		this.highestGmob = highestGmob;
	}	

	public double getTarget() {
		if (target == 0.0) {
			target = price;
		}
		return target;
	}

	public void setTarget(double target) {
		this.target = target;
	}

	public void addGmob(double gmob, int checkLastGmobCount) {
		String[] gmobArray = StringUtil.generateArrayFromDeliminatedString('|', lastGmobArray);
		
		if (gmobArray == null) {
			gmobArray = new String[checkLastGmobCount];
		}
		else if (gmobArray.length != checkLastGmobCount){
			String[] tempArray = new String[checkLastGmobCount];
			for (int i = 0; i < checkLastGmobCount; i++) {
				if (i > gmobArray.length - 1) {
					tempArray[i] = null;
				}
				else {
					tempArray[i] = gmobArray[i];
				}
			}
			
			gmobArray = tempArray;
		}
		
		for (int i = gmobArray.length - 1; i > 0; i--) {
			gmobArray[i] = gmobArray[i - 1];			
		}
		
		gmobArray[0] = String.valueOf(gmob);
		
		lastGmobArray = StringUtil.generateDeliminatedString('|', gmobArray);
	}
	
	public void addGmoa(double gmoa, int checkLastGmobCount) {
		String[] gmoaArray = StringUtil.generateArrayFromDeliminatedString('|', lastGmoaArray);
		
		if (gmoaArray == null) {
			gmoaArray = new String[checkLastGmobCount];
		}
		else if (gmoaArray.length != checkLastGmobCount){
			String[] tempArray = new String[checkLastGmobCount];
			
			for (int i = 0; i < checkLastGmobCount; i++) {
				if (i > gmoaArray.length - 1) {
					tempArray[i] = null;
				}
				else {
					tempArray[i] = gmoaArray[i];
				}
			}
			
			gmoaArray = tempArray;
		}
		
		for (int i = gmoaArray.length - 1; i > 0; i--) {
			gmoaArray[i] = gmoaArray[i - 1];			
		}
		
		gmoaArray[0] = String.valueOf(gmoa);
		
		lastGmoaArray = StringUtil.generateDeliminatedString('|', gmoaArray);
	}
	
	public List<Double> generateLastGmoaList() {
		List<Double> gmoaValueList = new ArrayList<>();
		
		if (lastGmoaArray == null) {
			return gmoaValueList;
		}
		
		String[] gmoaStringArr = StringUtil.generateArrayFromDeliminatedString('|', lastGmoaArray);
		
		for (String gmoa : gmoaStringArr) {
			Double gmoaValue = NumberDisplayUtil.parse(gmoa);
			if (gmoaValue != null) {
				gmoaValueList.add(gmoaValue);
			}
		}
		
		return gmoaValueList;		
	}
	
	public List<Double> generateLastGmobList() {
		List<Double> gmobValueList = new ArrayList<>();
		
		if (lastGmobArray == null) {
			return gmobValueList;
		}
		
		String[] gmoaStringArr = StringUtil.generateArrayFromDeliminatedString('|', lastGmobArray);		
		
		for (String gmob : gmoaStringArr) {
			Double gmobValue = NumberDisplayUtil.parse(gmob);
			if (gmobValue != null) {
				gmobValueList.add(gmobValue);
			}
		}
		
		return gmobValueList;		
	}
}
