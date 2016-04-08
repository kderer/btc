package net.kadirderer.btc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.api.buyorder.BuyOrderResult;
import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.cancelorder.CancelOrderResult;
import net.kadirderer.btc.api.cancelorder.CancelOrderService;
import net.kadirderer.btc.api.orderbook.OrderbookService;
import net.kadirderer.btc.api.queryorder.QueryOrderResult;
import net.kadirderer.btc.api.queryorder.QueryOrderService;
import net.kadirderer.btc.api.sellorder.SellOrderResult;
import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.db.criteria.UserOrderCriteria;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.util.email.Email;
import net.kadirderer.btc.util.email.MailSendService;
import net.kadirderer.btc.util.enumaration.OrderStatus;
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
	private MailSendService emailService;

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
	protected BuyOrderResult buyOrder(UserOrder order) throws Exception {
		return boService.buyOrder(order);
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
	protected SellOrderResult sellOrder(UserOrder order) throws Exception {
		return soService.sellOrder(order);
	}

	@Override
	protected List<UserOrder> queryPendingAutoTradeOrders(String username) throws Exception {
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");	
		
		return uoDao.findPending(username, btcChina.getId());
	}

	@Override
	protected void sendMailForException(Exception e) {
		emailService.sendMailForException(e);
	}

	@Override
	protected void sendMail(Email email) {
		emailService.sendMail(email);
	}

	@Override	
	protected void updatePartnerts(int userOrderId, int partnerUserOrderId) {
		uoDao.updatePartnerId(userOrderId, partnerUserOrderId);
		uoDao.updatePartnerId(partnerUserOrderId, userOrderId);
	}

	@Override
	protected UserOrder findPendingPartner(int userOrderId) {
		UserOrderCriteria userOrderCriteria = new UserOrderCriteria();
		userOrderCriteria.addPartnerId(userOrderId);
		userOrderCriteria.addStatus(OrderStatus.PENDING.getCode());
		
		List<UserOrder> resultList = uoDao.findByCriteria(userOrderCriteria);
		
		if (resultList == null || resultList.size() <= 0) {
			return null;
		}
		else {
			return resultList.get(0);
		}
	}

	@Override
	protected void updatePartnerIdWithNewId(int oldUserOrderId, Integer newUserOrderId) {
		uoDao.updatePartnerIdWithNewId(oldUserOrderId, newUserOrderId);
	}

}
