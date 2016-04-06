package net.kadirderer.btc.db.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.kadirderer.btc.db.util.DateUtil;

public class UserOrderCriteria {
	
	private List<Integer> idList;	
	private List<String> usernameList;	
	private List<Integer> platformIdList;	
	private List<Character> orderTypeList;	
	private Double priceStart;
	private Double priceEnd;	
	private Double amountStart;
	private Double amountEnd;	
	private Double completedAmountStart;
	private Double completedAmountEnd;
	private Double profitStart;
	private Double profitEnd;	
	private List<String> returnIdList;	
	private List<Character> statusList;	
	private Date createDateStart;
	private Date createDateEnd;	
	private Date updateDateStart;
	private Date updateDateEnd;
	private List<Integer> partnerIdList;
	private int pageSize;
	private int pageNumber;
	
	
	public Double getAmountEnd() {
		return amountEnd;
	}
	
	public void setAmountEnd(Double amountEnd) {
		this.amountEnd = amountEnd;
	}

	public List<Integer> getIdList() {
		return idList;
	}

	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}
	
	public void addId(Integer id) {
		if(idList == null) {
			idList = new ArrayList<Integer>();
		}
		
		idList.add(id);
	}

	public List<String> getUsernameList() {
		return usernameList;
	}

	public void setUsernameList(List<String> usernameList) {
		this.usernameList = usernameList;
	}
	
	public void addUsername(String Username) {
		if(usernameList == null) {
			usernameList = new ArrayList<String>();
		}
		
		usernameList.add(Username);
	}

	public List<Integer> getPlatformIdList() {
		return platformIdList;
	}

	public void setPlatformIdList(List<Integer> platformIdList) {
		this.platformIdList = platformIdList;
	}
	
	public void addPlatformId(Integer platformId) {
		if(platformIdList == null) {
			platformIdList = new ArrayList<Integer>();
		}
		
		platformIdList.add(platformId);
	}

	public List<Character> getOrderTypeList() {
		return orderTypeList;
	}

	public void setOrderTypeList(List<Character> orderTypeList) {
		this.orderTypeList = orderTypeList;
	}
	
	public void addOrderType(char orderType) {
		if(orderTypeList == null) {
			orderTypeList = new ArrayList<Character>();
		}
		
		orderTypeList.add(orderType);
	}

	public Double getPriceStart() {
		return priceStart;
	}

	public void setPriceStart(Double priceStart) {
		this.priceStart = priceStart;
	}

	public Double getPriceEnd() {
		return priceEnd;
	}

	public void setPriceEnd(Double priceEnd) {
		this.priceEnd = priceEnd;
	}

	public Double getAmountStart() {
		return amountStart;
	}

	public void setAmountStart(Double amountStart) {
		this.amountStart = amountStart;
	}

	public Double getCompletedAmountStart() {
		return completedAmountStart;
	}

	public void setCompletedAmountStart(Double completedAmountStart) {
		this.completedAmountStart = completedAmountStart;
	}

	public Double getCompletedAmountEnd() {
		return completedAmountEnd;
	}

	public void setCompletedAmountEnd(Double completedAmountEnd) {
		this.completedAmountEnd = completedAmountEnd;
	}

	public Double getProfitStart() {
		return profitStart;
	}

	public void setProfitStart(Double profitStart) {
		this.profitStart = profitStart;
	}

	public Double getProfitEnd() {
		return profitEnd;
	}

	public void setProfitEnd(Double profitEnd) {
		this.profitEnd = profitEnd;
	}

	public List<String> getReturnIdList() {
		return returnIdList;
	}

	public void setReturnIdList(List<String> returnIdList) {
		this.returnIdList = returnIdList;
	}
	
	public void addReturnId(String returnId) {
		if(returnIdList == null) {
			returnIdList = new ArrayList<String>();
		}
		
		returnIdList.add(returnId);
	}

	public List<Character> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Character> statusList) {
		this.statusList = statusList;
	}
	
	public void addStatus(char status) {
		if(statusList == null) {
			statusList = new ArrayList<Character>();
		}
		
		statusList.add(status);
	}

	public Date getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(Date createDateStart) {
		this.createDateStart = createDateStart;
	}
	
	public void setCreateDateStartStr(String createDateStart) {
		this.createDateStart = DateUtil.parse(createDateStart);
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}
	
	public void setCreateDateEndStr(String createDateEnd) {
		this.createDateEnd = DateUtil.parse(createDateEnd);
	}

	public Date getUpdateDateStart() {
		return updateDateStart;
	}

	public void setUpdateDateStart(Date updateDateStart) {
		this.updateDateStart = updateDateStart;
	}

	public Date getUpdateDateEnd() {
		return updateDateEnd;
	}

	public void setUpdateDateEnd(Date updateDateEnd) {
		this.updateDateEnd = updateDateEnd;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public void setPartnerIdList(List<Integer> partnerIdList) {
		this.partnerIdList = partnerIdList;
	}
	
	public List<Integer> getPartnerIdList() {
		return partnerIdList;
	}
	
	public void addPartnerId(int partnerId) {
		if (this.partnerIdList == null) {
			this.partnerIdList = new ArrayList<Integer>();
		}
		
		this.partnerIdList.add(partnerId);
	}

}
