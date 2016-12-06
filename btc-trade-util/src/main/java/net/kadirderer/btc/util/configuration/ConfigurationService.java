package net.kadirderer.btc.util.configuration;

import org.apache.commons.configuration2.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {
	
	private static final String RECORDGROUPJOB_ENABLED = "recordgroupjob_enabled";
	private static final String AUTOTRADE_ENABLED = "autotrade_enabled";
	private static final String SELLORDER_DELTA = "sellorder_delta";
	private static final String SELLREORDER_DELTA = "sellreorder_delta";
	private static final String SELLORDER_TIME_LIMIT = "sellorder_time_limit";
	private static final String BUYORDER_DELTA = "buyorder_delta";
	private static final String BUYREORDER_DELTA = "buyreorder_delta";
	private static final String BUYORDER_TIME_LIMIT = "buyorder_time_limit";
	private static final String LAST_BID_PRICE_CHECK_DELTA = "last_bid_price_check_delta";
	private static final String NON_AUTO_UPDATE_TIMELIMIT = "non_auto_update_timelimit";
	private static final String NON_AUTO_UPDATE_ORDER_DELTA = "non_auto_update_order_delta";
	private static final String BEST_GMOB_CHECK_DELTA = "best_gmob_check_delta";
	private static final String BASEPRICE_HIGHEST_BID_CHECK_DELTA = "baseprice_highest_bid_check_delta";
	private static final String OBR_TIME_LIMIT = "obr_time_limit";
	private static final String CHECK_LAST_GMOB_COUNT = "check_last_gmob_count";
	private static final String NON_PROFIT_BUY_ORDER_ALLOWED = "non_profit_buy_order_allowed";
	private static final String NON_PROFIT_SELL_ORDER_ALLOWED = "non_profit_sell_order_allowed";
	private static final String WAIT_TIME_AFTER_CANCEL_SELL_ORDER = "wait_time_after_cancel_sell_order";
	private static final String WAIT_TIME_AFTER_CANCEL_BUY_ORDER = "wait_time_after_cancel_buy_order";
	private static final String AUTO_UPDATE_CHECK_INTERVAL = "auto_update_check_interval";
	private static final String AUTO_TRADE_CHECK_INTERVAL = "auto_trade_check_interval";
	private static final String NON_PROFIT_BUY_ORDER_ALLOWED_IF_PARENT_HAS_PROFIT = "non_profit_buy_order_allowed_if_parent_has_profit";
	private static final String NON_PROFIT_SELL_ORDER_ALLOWED_IF_PARENT_HAS_PROFIT = "non_profit_sell_order_allowed_if_parent_has_profit";

	@Autowired
	private Configuration configuration;
	
	public String getStringValue(String key) {
		return configuration.getString(key);
	}
	
	public Double getDoubleValue(String key) {
		return configuration.getDouble(key);
	}
	
	public Integer getIntegerValue(String key) {
		return configuration.getInt(key);
	}
	
	public Boolean getBooleanValue(String key) {
		return configuration.getBoolean(key);
	}
	
	public boolean isRecordGroupJobEnabled() {
		return configuration.getBoolean(RECORDGROUPJOB_ENABLED);
	}
	
	public boolean isAutoTradeEnabled() {
		return configuration.getBoolean(AUTOTRADE_ENABLED);
	}
	
	public double getSellOrderDelta() {
		return configuration.getDouble(SELLORDER_DELTA);
	}
	
	public int getSellOrderTimeLimit() {
		return configuration.getInt(SELLORDER_TIME_LIMIT);
	}
	
	public double getSellReOrderDelta() {
		return configuration.getDouble(SELLREORDER_DELTA);
	}
	
	public double getBuyOrderDelta() {
		return configuration.getDouble(BUYORDER_DELTA);
	}
	
	public int getBuyOrderTimeLimit() {
		return configuration.getInt(BUYORDER_TIME_LIMIT);
	}
	
	public double getBuyReOrderDelta() {
		return configuration.getDouble(BUYREORDER_DELTA);
	}
	
	public double getLastBidPriceCheckDelta() {
		return configuration.getDouble(LAST_BID_PRICE_CHECK_DELTA);
	}
	
	public int getNonAutoUpdateTimeLimit() {
		return configuration.getInt(NON_AUTO_UPDATE_TIMELIMIT);
	}
	
	public double getNonAutoUpdateOrderDelta() {
		return configuration.getDouble(NON_AUTO_UPDATE_ORDER_DELTA);
	}
	
	public double getBestGmobCheckDelta() {
		return configuration.getDouble(BEST_GMOB_CHECK_DELTA);
	}
	
	public double getBasePriceHighestBidCheckDelta() {
		return configuration.getDouble(BASEPRICE_HIGHEST_BID_CHECK_DELTA);
	}
	
	public int getObrTimeLimit() {
		return configuration.getInt(OBR_TIME_LIMIT);
	}
	
	public int getCheckLastGmobCount() {
		return configuration.getInt(CHECK_LAST_GMOB_COUNT);
	}
	
	public boolean isNonProfitBuyOrderAllowed() {
		return configuration.getBoolean(NON_PROFIT_BUY_ORDER_ALLOWED);
	}
	
	public boolean isNonProfitSellOrderAllowed() {
		return configuration.getBoolean(NON_PROFIT_SELL_ORDER_ALLOWED);
	}
	
	public int getWaitTimeAfterCancelSellOrder() {
		return configuration.getInt(WAIT_TIME_AFTER_CANCEL_SELL_ORDER);
	}
	
	public int getWaitTimeAfterCancelBuyOrder() {
		return configuration.getInt(WAIT_TIME_AFTER_CANCEL_BUY_ORDER);
	}
	
	public int getAutoUpdateCheckInterval() {
		return configuration.getInt(AUTO_UPDATE_CHECK_INTERVAL);
	}
	
	public int getAutoTradeCheckInterval() {
		return configuration.getInt(AUTO_TRADE_CHECK_INTERVAL);
	}
	
	public boolean isNonProfitBuyOrderAllowedIfParentHasProfit() {
		return configuration.getBoolean(NON_PROFIT_BUY_ORDER_ALLOWED_IF_PARENT_HAS_PROFIT);
	}
	
	public boolean isNonProfitSellOrderAllowedIfParentHasProfit() {
		return configuration.getBoolean(NON_PROFIT_SELL_ORDER_ALLOWED_IF_PARENT_HAS_PROFIT);
	}
}
