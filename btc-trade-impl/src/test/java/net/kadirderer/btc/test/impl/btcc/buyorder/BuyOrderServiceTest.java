package net.kadirderer.btc.test.impl.btcc.buyorder;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.kadirderer.btc.api.buyorder.BuyOrderResult;
import net.kadirderer.btc.api.buyorder.BuyOrderService;
import net.kadirderer.btc.db.model.UserOrder;
import net.kadirderer.btc.test.config.BaseTestConfig;

public class BuyOrderServiceTest extends BaseTestConfig {
	
	@Autowired
	private BuyOrderService buyOrderService;
	
	@Test
	public void buyOrderTest() throws Exception {
		UserOrder order = new UserOrder();
		order.setAmount(0.01);
		order.setPrice(200);
		order.setUsername("kadir");
		order.setPlatformId(9);
		
		BuyOrderResult result = buyOrderService.buyOrder(order);
		
		Assert.assertNotNull(result.getUserOrderId());
	}

}
