package net.kadirderer.btc.impl.queryorder;

import java.util.LinkedHashMap;
import java.util.Map;

import net.kadirderer.btc.api.queryorder.QueryOrderResult;
import net.kadirderer.btc.api.queryorder.QueryOrderStatus;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.impl.util.btcc.BtcChinaErrorResult;

public class BtcChinaQueryOrderResult extends QueryOrderResult {
	
	public static final String ORDER = "order";
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String PRICE = "price";
	public static final String CURRENCY = "currency";
	public static final String AMOUNT = "amount";
	public static final String ORIGINAL_AMOUNT = "amount_original";
	public static final String DATE = "date";
	public static final String STATUS = "status";
	
	private BtcChinaErrorResult error;
	private Map<String, Object> result;
	private String id;
	private double lastCompletedAmount;
	private UserOrder userOrder;
	
	public BtcChinaErrorResult getError() {
		return error;
	}
	
	public void setError(BtcChinaErrorResult error) {
		this.error = error;
	}
	
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public double getAmount() {
		if(getOrder() != null) {
			return Double.valueOf((String)getOrder().get(AMOUNT));
		}
		
		return 0;
	}
	
	public double getOriginalAmount() {
		if(getOrder() != null) {
			return Double.valueOf((String)getOrder().get(ORIGINAL_AMOUNT));
		}
		
		return 0;
	}
	
	public double getPrice() {
		if(getOrder() != null) {
			return Double.valueOf((String)getOrder().get(PRICE));
		}
		
		return 0;
	}
	
	@Override
	public String getType() {
		if(getOrder() != null) {
			return (String)getOrder().get(TYPE);
		}
		
		return null;
	}	
	
	public double getLastCompletedAmount() {
		return lastCompletedAmount;
	}

	public void setLastCompletedAmount(double lastCompleted) {
		this.lastCompletedAmount = lastCompleted;
	}

	@Override
	public QueryOrderStatus getStatus() {		
		if(getOrder() != null) {
			String status = (String)getOrder().get(STATUS);			
			if("open".equals(status)) {
				return QueryOrderStatus.OPEN;				
			} else if ("closed".equals(status)) {
				return QueryOrderStatus.CLOSED;				
			} else if ("cancelled".equals(status)) {
				return QueryOrderStatus.CANCELLED;				
			} else if ("pending".equals(status)) {
				return QueryOrderStatus.PENDING;				
			} else if ("error".equals(status)) {
				return QueryOrderStatus.ERROR;				
			} else if("insufficient_balance".equals(status)) {
				return QueryOrderStatus.INSUFFICIENT_BALANCE;				
			}
		}
		
		return null;		
	}	
	
	
	@SuppressWarnings("unchecked")
	private LinkedHashMap<String, Object> getOrder() {
		if(result != null) {
			return (LinkedHashMap<String, Object>) result.get(ORDER);
		}
		
		return null;
	}

	@Override
	public double getCompletedAmount() {
		return getOriginalAmount() - getAmount();
	}
	
	@Override
	public UserOrder getUserOrder() {
		return userOrder;
	}

	public void setUserOrder(UserOrder userOrder) {
		this.userOrder = userOrder;
	}
	
}
