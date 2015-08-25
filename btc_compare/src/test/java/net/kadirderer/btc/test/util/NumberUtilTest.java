package net.kadirderer.btc.test.util;

import net.kadirderer.btc.api.util.btcchina.NumberUtil;

import org.junit.Assert;
import org.junit.Test;

public class NumberUtilTest {
	
	@Test
	public void testFormat() {
		
		Assert.assertEquals(1.4534, NumberUtil.format(1.453456), 0);
		
		Assert.assertEquals(1234.453, NumberUtil.format(1234.453), 0);
		
	}

}
