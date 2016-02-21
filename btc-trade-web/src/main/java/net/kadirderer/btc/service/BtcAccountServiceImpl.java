package net.kadirderer.btc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.api.buyorder.BuyOrderResult;
import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoResult;
import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoService;
import net.kadirderer.btc.api.sellorder.SellOrderResult;
import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.util.enumaration.OrderStatus;
import net.kadirderer.btc.web.dto.BuyOrderDto;
import net.kadirderer.btc.web.dto.SellOrderDto;

@Service
public class BtcAccountServiceImpl implements BtcAccountService {
	
	@Autowired
	private QueryAccountInfoService queryAccountInfoService;
	
	@Autowired
	private BuyOrderService buyOrderService;
	
	@Autowired
	private SellOrderService sellOrderService;
	
	@Autowired
	private BtcPlatformDao btcPlatformDao;

	@Override
	public QueryAccountInfoResult queryAccountInfo(String username, String platformCode) throws Exception {
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");
		QueryAccountInfoResult accountInfoResult = queryAccountInfoService.queryAccountInfo(username);
		accountInfoResult.setPlatformName(btcChina.getName());
		
		return accountInfoResult;
	}

	@Override
	public BuyOrderResult buyOrder(BuyOrderDto buyOrder) throws Exception {
		UserOrder order = new UserOrder();
		order.setUsername(buyOrder.getUsername());
		order.setPrice(buyOrder.getPrice());
		order.setAmount(buyOrder.getAmount());
		
		if (!buyOrder.isAutoTrade()) {
			order.setStatus(OrderStatus.MANUAL.getCode());
		}
		
		return buyOrderService.buyOrder(order);
	}

	@Override
	public SellOrderResult sellOrder(SellOrderDto sellOrder) throws Exception {
		UserOrder order = new UserOrder();
		order.setUsername(sellOrder.getUsername());
		order.setPrice(sellOrder.getPrice());
		order.setAmount(sellOrder.getAmount());
		
		if (!sellOrder.isAutoTrade()) {
			order.setStatus(OrderStatus.MANUAL.getCode());
		}
		
		return sellOrderService.sellOrder(order);
	}	
	
}
