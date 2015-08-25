package net.kadirderer.btc.api.marketdepth;

import org.springframework.stereotype.Service;

import net.kadirderer.btc.api.ApiType;
import net.kadirderer.btc.api.util.btcchina.BtcChinaApiCallable;
import net.kadirderer.btc.api.util.btcchina.BtcChinaApiUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BtcChinaMarketDepthServiceImpl implements MarketDepthService, BtcChinaApiCallable {

	@Override
	public MarketDepthResult getMarketDepth(String username) throws Exception {
		String jsonResult = BtcChinaApiUtil.callApi(this, username);

		ObjectMapper om = new ObjectMapper();
		BtcChinaMarketDepthResult result = om.readValue(jsonResult, BtcChinaMarketDepthResult.class);
		
		return result;
	}

	@Override
	public String getApiName() {
		// TODO Auto-generated method stub
		return "getMarketDepth2";
	}

	@Override
	public String getParameters(String... parameters) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getJSONRequestParameters(String... parameters) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public ApiType getApiType() {
		// TODO Auto-generated method stub
		return ApiType.MARKETDEPTH;
	}

}
