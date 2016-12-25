package net.kadirderer.btc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.api.buyorder.BuyOrderResult;
import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.cancelorder.CancelOrderResult;
import net.kadirderer.btc.api.cancelorder.CancelOrderService;
import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.api.orderbook.OrderbookService;
import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoResult;
import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoService;
import net.kadirderer.btc.api.queryorder.QueryOrderResult;
import net.kadirderer.btc.api.queryorder.QueryOrderService;
import net.kadirderer.btc.api.sellorder.SellOrderResult;
import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.api.ticker.TickerService;
import net.kadirderer.btc.api.updateorder.UpdateOrderResult;
import net.kadirderer.btc.api.updateorder.UpdateOrderService;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.dao.StatisticsDao;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.Statistics;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.util.email.Email;
import net.kadirderer.btc.util.email.MailSendService;
import net.kadirderer.btc.util.enumaration.OrderType;

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
	
	@Autowired
	private QueryAccountInfoService queryAccountInfoService;
	
	@Autowired
	private UpdateOrderService updateOrderService;
	
	@Autowired
	private MailSendService emailService;
	
	@Autowired
	private MarketDepthService marketDepthService;
	
	@Autowired
	private TickerService tickerService;
	
	@Autowired
	private StatisticsDao statisticsDao;

	@Override
	public double getHighestBid() throws Exception {
		
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");	
		
		return obService.query(btcChina.getId()).getHighestBid();
	}

	@Override
	public double getLowestAsk() throws Exception {
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");	
		
		return obService.query(btcChina.getId()).getHighestAsk();
	}

	@Override
	public UserOrder getLatestPendingBuy(String username) throws Exception {
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");
		return uoDao.findLastPending(username, btcChina.getId(), OrderType.BUY.getCode());
	}

	@Override
	public UserOrder getLatestPendingSell(String username) throws Exception {
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");
		return uoDao.findLastPending(username, btcChina.getId(), OrderType.SELL.getCode());
	}

	@Override
	public BuyOrderResult buyOrder(UserOrder order) throws Exception {
		return boService.buyOrder(order);
	}

	@Override
	public QueryOrderResult queryOrder(String username, String orderId, boolean updateDbStatus)
			throws Exception {
		return qoService.queryOrder(username, orderId, updateDbStatus);
	}

	@Override
	public CancelOrderResult cancelOrder(String username, String orderId)
			throws Exception {
		return coService.cancelOrder(username, orderId);
	}

	@Override
	public SellOrderResult sellOrder(UserOrder order) throws Exception {
		return soService.sellOrder(order);
	}

	@Override
	public List<UserOrder> queryPendingAutoTradeOrders(String username) throws Exception {
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");	
		
		return uoDao.findPending(username, btcChina.getId());
	}

	@Override
	public void sendMailForException(Exception e) {
		emailService.sendMailForException(e);
	}

	@Override
	public void sendMail(Email email) {
		emailService.sendMail(email);
	}

	@Override
	public QueryAccountInfoResult queryAccountInfo(String username) throws Exception {
		return queryAccountInfoService.queryAccountInfo(username);
	}

	@Override
	public UserOrder findUserOrderById(int userOrderId) {
		return uoDao.findById(userOrderId);
	}

	@Override
	public UpdateOrderResult updateOrder(UserOrder userOrder, double amount, double price) throws Exception {
		return updateOrderService.updateOrder(userOrder, amount, price);
	}

	@Override
	public UserOrder saveUserOrder(UserOrder userOrder) {
		return uoDao.save(userOrder);
	}

	@Override
	public double[] getMaxAndGeometricMean(String username) throws Exception {
		return marketDepthService.getMarketDepth(username).getMaxAndGeometricMean();
	}

	@Override
	public double get24HoursHigh() throws Exception {
		return tickerService.getTicker(9).get24HoursHigh();
	}

	@Override
	public List<Statistics> findLastStatistics(int count) {
		return statisticsDao.findLatestNStatistics(count);
	}
	
}
