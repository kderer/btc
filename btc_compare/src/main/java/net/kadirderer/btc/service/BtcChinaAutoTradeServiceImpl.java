package net.kadirderer.btc.service;

import net.kadirderer.btc.api.OrderType;
import net.kadirderer.btc.api.buyorder.BuyOrderResult;
import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.cancelorder.CancelOrderResult;
import net.kadirderer.btc.api.cancelorder.CancelOrderService;
import net.kadirderer.btc.api.orderbook.OrderbookService;
import net.kadirderer.btc.api.queryorder.QueryOrderResult;
import net.kadirderer.btc.api.queryorder.QueryOrderService;
import net.kadirderer.btc.api.sellorder.SellOrderResult;
import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.UserOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BtcChinaAutoTradeServiceImpl extends AutoTradeService {
	
	@Autowired
	private OrderbookService obService;
	
	@Autowired
	private QueryOrderService qoService;
	
	@Autowired
	private CancelOrderService coService;
	
	@Autowired
	private BuyOrderService boService;
	
	@Autowired
	private SellOrderService soService;
	
	@Autowired
	private BtcPlatformDao btcPlatformDao;
	
	@Autowired
	private UserOrderDao uoDao;	

	@Override
	protected double getHighestBid() throws Exception {
		
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");	
		
		return obService.query(btcChina.getId()).getHighestBid();
	}

	@Override
	protected double getLowestAsk() throws Exception {
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");	
		
		return obService.query(btcChina.getId()).getHighestAsk();
	}

	@Override
	protected UserOrder getLatestPendingBuy(String username) throws Exception {
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");
		return uoDao.findLastPending(username, btcChina.getId(), OrderType.BUY.getCode());
	}

	@Override
	protected UserOrder getLatestPendingSell(String username) throws Exception {
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");
		return uoDao.findLastPending(username, btcChina.getId(), OrderType.SELL.getCode());
	}

	@Override
	protected BuyOrderResult buyOrder(String username, double price,
			double amount) throws Exception {
		return boService.buyOrder(username, price, amount);
	}

	@Override
	protected QueryOrderResult queryOrder(String username, String orderId)
			throws Exception {
		return qoService.queryOrder(username, orderId);
	}

	@Override
	protected CancelOrderResult cancelOrder(String username, String orderId)
			throws Exception {
		return coService.cancelOrder(username, orderId);
	}

	@Override
	protected SellOrderResult sellOrder(String username, double price,
			double amount) throws Exception {
		return soService.sellOrder(username, price, amount, 0);
	}

}
