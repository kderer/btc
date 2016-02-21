package net.kadirderer.btc.service;

import net.kadirderer.btc.api.buyorder.BuyOrderResult;
import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoResult;
import net.kadirderer.btc.api.sellorder.SellOrderResult;
import net.kadirderer.btc.web.dto.BuyOrderDto;
import net.kadirderer.btc.web.dto.SellOrderDto;

public interface BtcAccountService {
	
	public QueryAccountInfoResult queryAccountInfo(String username, String platformCode) throws Exception;
	
	public BuyOrderResult buyOrder(BuyOrderDto buyOrder) throws Exception;
	
	public SellOrderResult sellOrder(SellOrderDto sellOrder) throws Exception ;	

}
