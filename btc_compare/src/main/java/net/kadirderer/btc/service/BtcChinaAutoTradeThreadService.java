package net.kadirderer.btc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.cancelorder.CancelOrderService;
import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.api.orderbook.OrderbookService;
import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoService;
import net.kadirderer.btc.api.queryorder.QueryOrderService;
import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.model.BtcPlatform;

@Service
public class BtcChinaAutoTradeThreadService extends AutoTradeThreadService {

	@Autowired
	private BuyOrderService buyOrderService;
	
	@Autowired
	private CancelOrderService cancelOrderService;
	
	@Autowired
	private SellOrderService sellOrderService;
	
	@Autowired
	private QueryAccountInfoService queryAccountInfoService;
	
	@Autowired
	private QueryOrderService queryOrderService;
	
	@Autowired
	private OrderbookService orderBookService;
	
	@Autowired
	private MarketDepthService marketDepthService;
	
	@Autowired
	private BtcPlatformDao btcPlatformDao;
	
	@Override
	public SellOrderService getSellOrderService() {
		// TODO Auto-generated method stub
		return sellOrderService;
	}

	@Override
	public BuyOrderService getBuyOrderService() {
		// TODO Auto-generated method stub
		return buyOrderService;
	}

	@Override
	public CancelOrderService getCancelOrderService() {
		// TODO Auto-generated method stub
		return cancelOrderService;
	}

	@Override
	public QueryOrderService getQueryOrderService() {
		// TODO Auto-generated method stub
		return queryOrderService;
	}

	@Override
	public OrderbookService getOrderBookService() {
		// TODO Auto-generated method stub
		return orderBookService;
	}

	@Override
	public QueryAccountInfoService getQueryAccountInfoService() {
		// TODO Auto-generated method stub
		return queryAccountInfoService;
	}

	@Override
	public double getBtcBalanceMinimumLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getCurrencyMinimumLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPlatformId() {
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");
		return btcChina.getId();
	}

	@Override
	public double getDeltaForTrade() {
		return 0.15;
	}

	@Override
	public int getPeriod() {
		return 10;
	}
	
	@Override
	public MarketDepthService getMarketDepthService() {
		// TODO Auto-generated method stub
		return marketDepthService;
	}

}
