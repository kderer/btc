package net.kadirderer.btc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.api.buyorder.BuyOrderResult;
import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoResult;
import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoService;
import net.kadirderer.btc.api.sellorder.SellOrderResult;
import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.util.configuration.ConfigurationService;
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
	private MarketDepthService marketDepthService;
	
	@Autowired
	private ConfigurationService cfgService;
	
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
		double highestBid = marketDepthService.getMarketDepth(buyOrder.getUsername()).getHighestBid();
		
		UserOrder order = new UserOrder();
		order.setUsername(buyOrder.getUsername());
		if ((buyOrder.getPrice() == null || buyOrder.getPrice() == 0) && buyOrder.isAutoUpdate()) {
			order.setPrice(highestBid - cfgService.getBuyOrderDelta());
		}
		else if (buyOrder.getPrice() == null || buyOrder.getPrice() == 0) {
			order.setPrice(highestBid);
		}
		else {
			order.setPrice(buyOrder.getPrice());
		}
		order.setAmount(buyOrder.getAmount());
		order.setAutoTrade(buyOrder.isAutoTrade());
		order.setAutoUpdate(buyOrder.isAutoUpdate());
		order.setBasePrice(highestBid);
		
		if (order.isAutoUpdate()) {
			order.setStatus(OrderStatus.NEW.getCode());
		}
		
		return buyOrderService.buyOrder(order);
	}

	@Override
	public SellOrderResult sellOrder(SellOrderDto sellOrder) throws Exception {
		double highestBid = marketDepthService.getMarketDepth(sellOrder.getUsername()).getHighestBid();
		
		UserOrder order = new UserOrder();
		order.setUsername(sellOrder.getUsername());
		if ((sellOrder.getPrice() == null || sellOrder.getPrice() == 0) && sellOrder.isAutoUpdate()) {
			order.setPrice(highestBid + cfgService.getSellOrderDelta());
		}
		else if (sellOrder.getPrice() == null || sellOrder.getPrice() == 0) {
			order.setPrice(highestBid);
		}
		else {
			order.setPrice(sellOrder.getPrice());
		}
		order.setAmount(sellOrder.getAmount());
		order.setAutoUpdate(sellOrder.isAutoUpdate());
		order.setAutoTrade(sellOrder.isAutoTrade());
		order.setBasePrice(highestBid);
		
		if (order.isAutoUpdate()) {
			order.setStatus(OrderStatus.NEW.getCode());
		}
		
		return sellOrderService.sellOrder(order);
	}	
	
}
