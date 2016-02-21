package net.kadirderer.btc.impl.marketdepth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.kadirderer.btc.api.marketdepth.MarketDepthResult;
import net.kadirderer.btc.api.marketdepth.MarketDepthService;
import net.kadirderer.btc.impl.util.btcc.BtcChinaApiCallable;
import net.kadirderer.btc.impl.util.btcc.BtcChinaPlatformClient;
import net.kadirderer.btc.util.enumaration.ApiType;

@Service
public class BtcChinaMarketDepthService implements MarketDepthService, BtcChinaApiCallable {
	
	@Autowired
	private BtcChinaPlatformClient btccClient;

	@Override
	public MarketDepthResult getMarketDepth(String username) throws Exception {
		String jsonResult = btccClient.callApi(this, username);

		ObjectMapper om = new ObjectMapper();
		BtcChinaMarketDepthResult result = om.readValue(jsonResult, BtcChinaMarketDepthResult.class);
		
		return result;
	}

	@Override
	public String getApiName() {
		return "getMarketDepth2";
	}

	@Override
	public String getParameters(String... parameters) {
		return "";
	}

	@Override
	public String getJSONRequestParameters(String... parameters) {
		return "";
	}

	@Override
	public ApiType getApiType() {
		return ApiType.MARKETDEPTH;
	}

}
