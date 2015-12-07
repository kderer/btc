package net.kadirderer.btc.api.sellorder;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.kadirderer.btc.api.ApiType;
import net.kadirderer.btc.api.OrderStatus;
import net.kadirderer.btc.api.OrderType;
import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.api.cancelorder.CancelOrderService;
import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.api.queryorder.QueryOrderService;
import net.kadirderer.btc.api.util.btcchina.BtcChinaApiCallable;
import net.kadirderer.btc.api.util.btcchina.BtcChinaApiUtil;
import net.kadirderer.btc.api.util.btcchina.BtcChinaErrorResult;
import net.kadirderer.btc.api.util.btcchina.NumberUtil;
import net.kadirderer.btc.db.dao.BtcPlatformDao;
import net.kadirderer.btc.db.dao.FailedSellOrderDao;
import net.kadirderer.btc.db.dao.UserOrderDao;
import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.db.model.FailedSellOrder;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.service.SellOrderThread;
import net.kadirderer.btc.service.UserOrderService;
import net.kadirderer.btc.util.email.EmailSendService;

@Service
public class BtcChinaSellOrderServiceImpl implements SellOrderService, BtcChinaApiCallable {

	@Autowired
	private BtcPlatformDao btcPlatformDao;
	
	@Autowired
	private UserOrderDao uoDao;
	
	@Autowired
	private FailedSellOrderDao failedOrderDao;
	
	@Autowired
	private BuyOrderService buyOrderService;
	
	@Autowired
	private CancelOrderService cancelOrderService;
	
	@Autowired
	private QueryOrderService queryOrderService;
	
	@Autowired
	private MarketDepthService marketDepthSevice;
	
	@Autowired
	private UserOrderService userOrderService;
	
	@Autowired
	private EmailSendService emailSendService;
	
	private Map<String, Double> profitMap = new Hashtable<String, Double>();

	@Override
	@Transactional
	public BtcChinaSellOrderResult sellOrder(String username, double price, double amount, double basePrice) throws Exception {
		
		amount = NumberUtil.format(amount);
		price = NumberUtil.format(price);
		
		if(amount < 0.001) {
			BtcChinaErrorResult error = new BtcChinaErrorResult();
			error.setCode("-32008");
			error.setMessage("Amount too small");
			
			BtcChinaSellOrderResult result = new BtcChinaSellOrderResult();
			result.setError(error);
			
			return result;
		}
		
		String jsonResult = BtcChinaApiUtil.callApi(this, username, String.valueOf(price), String.valueOf(amount));
		
		ObjectMapper om = new ObjectMapper();
		BtcChinaSellOrderResult result = om.readValue(jsonResult, BtcChinaSellOrderResult.class);
		
		saveOrder(result, username, price, amount);
		
		if(result.getError() == null) {
			SellOrderThread sot = new SellOrderThread();
			sot.setCancelOrderService(cancelOrderService);
			sot.setOrderId(result.getResult());
			sot.setQueryOrderService(queryOrderService);
			sot.setBuyOrderService(buyOrderService);
			sot.setSellOrderService(this);
			sot.setMarketDepthService(marketDepthSevice);
			sot.setUsername(username);
			sot.setBasePrice(basePrice);
			sot.setEmailSendService(emailSendService);
			
			Thread boThread = new Thread(sot);
			boThread.start();			
		} else { 
			
			FailedSellOrder failedOrder = new FailedSellOrder();
			failedOrder.setAmount(amount);
			failedOrder.setMessage(result.getError().getMessage());
            failedOrder.setPlatformId(btcPlatformDao.queryByCode("BTCCHINA").getId());
			failedOrder.setPrice(price);
			failedOrder.setUsername(username);
			failedOrder.setBasePrice(basePrice);
			failedOrder.setStatus('P');
            
            failedOrderDao.save(failedOrder);
		}
		
		return result;
	}
	
	private void saveOrder(BtcChinaSellOrderResult result, String username, double price, double amount) {		
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");
		
		UserOrder order = new UserOrder();
		order.setAmount(amount);
		order.setOrderType(OrderType.SELL.getCode());
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
		return "sellOrder2";
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
		return ApiType.SELLORDER;
	}

	@Override
	public double getProfit(String username) {
		Double profit = profitMap.get(username);
		if(profit == null) {
			return 0;
		}
		return profit;
	}

	@Override
	public void addProfit(String orderId, double profit) {
		BtcPlatform btcChina = btcPlatformDao.queryByCode("BTCCHINA");
		
		userOrderService.addProfit(profit, btcChina.getId(), orderId);
	}

}