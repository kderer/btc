package net.kadirderer.btc.api.buyorder;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.kadirderer.btc.api.ApiType;
import net.kadirderer.btc.api.OrderStatus;
import net.kadirderer.btc.api.OrderType;
import net.kadirderer.btc.api.cancelorder.CancelOrderService;
import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.api.queryorder.QueryOrderService;
import net.kadirderer.btc.api.sellorder.SellOrderService;
import net.kadirderer.btc.api.util.btcchina.BtcChinaApiCallable;
import net.kadirderer.btc.api.util.btcchina.BtcChinaApiUtil;
import net.kadirderer.btc.api.util.btcchina.BtcChinaErrorResult;
import net.kadirderer.btc.api.util.btcchina.NumberUtil;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.service.BuyOrderThread;

@Service
public class BtcChinaBuyOrderServiceImpl implements BuyOrderService, BtcChinaApiCallable {
	
	@Autowired
	private BtcPlatformDao btcPlatformDao;
	
	@Autowired
	private UserOrderDao uoDao;
	
	@Autowired
	private SellOrderService sellOrderService;
	
	@Autowired
	private CancelOrderService cancelOrderService;
	
	@Autowired
	private QueryOrderService queryOrderService;
	
	@Autowired
	private MarketDepthService marketDepthService;
	
	@Override
	@Transactional
	public BtcChinaBuyOrderResult buyOrder(String username, double price, double amount) throws Exception {
		
		if(amount < 0.001) {
			BtcChinaErrorResult error = new BtcChinaErrorResult();
			error.setCode("-32008");
			error.setMessage("Invalid amount");
			
			BtcChinaBuyOrderResult result = new BtcChinaBuyOrderResult();
			result.setError(error);
			
			return result;
		}
		
		amount = NumberUtil.format(amount);
		price = NumberUtil.format(price);
		
		String jsonResult = BtcChinaApiUtil.callApi(this, username, String.valueOf(price), String.valueOf(amount));
		
		ObjectMapper om = new ObjectMapper();
		BtcChinaBuyOrderResult result = om.readValue(jsonResult, BtcChinaBuyOrderResult.class);
		
		saveOrder(result, username, price, amount);
		
		if(result.getError() == null) {
			BuyOrderThread bot = new BuyOrderThread();
			bot.setCancelOrderService(cancelOrderService);
			bot.setOrderId(result.getResult());
			bot.setQueryOrderService(queryOrderService);
			bot.setSellOrderService(sellOrderService);
			bot.setBuyOrderService(this);
			bot.setUsername(username);
			bot.setMarketDepthService(marketDepthService);
			
			Thread boThread = new Thread(bot);
			boThread.start();			
		}

		return result;
	}
	
	private void saveOrder(BtcChinaBuyOrderResult result, String username, double price, double amount) {		
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");
		
		UserOrder order = new UserOrder();
		order.setAmount(amount);
		order.setOrderType(OrderType.BUY.getCode());
		order.setPlatformId(btcChina.getId());
		order.setPrice(price);
		order.setReturnId(result.getResult());
		if(result.getError() == null) {
			order.setStatus(OrderStatus.PENDING.getCode());
		}
		else {
			order.setStatus(OrderStatus.FAILED.getCode());
		}
		order.setUsername(username);
		order.setCreateDate(Calendar.getInstance().getTime());
		
		uoDao.save(order);
	}

	@Override
	public String getApiName() {
		return "buyOrder2";
	}

	@Override
	public String getParameters(String... parameters) {
		return parameters[0] + "," + parameters[1];
	}

	@Override
	public String getJSONRequestParameters(String... parameters) {
		return "\"" + parameters[0] + "\",\"" + parameters[1] + "\"";
	}

	@Override
	public ApiType getApiType() {
		return ApiType.BUYORDER;
	}

}