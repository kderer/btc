package net.kadirderer.btc.api.queryaccountinfo;

import net.kadirderer.btc.api.ApiType;
import net.kadirderer.btc.api.util.btcchina.BtcChinaApiCallable;
import net.kadirderer.btc.api.util.btcchina.BtcChinaApiUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BtcChinaQueryAccountInfoServiceImpl implements QueryAccountInfoService, BtcChinaApiCallable {

	@Override
	@Transactional
	public QueryAccountInfoResult queryAccountInfo(String username) throws Exception {
		String jsonResult = BtcChinaApiUtil.callApi(this, username);

		ObjectMapper om = new ObjectMapper();
		BtcChinaQueryAccountInfoResult result = om.readValue(jsonResult, BtcChinaQueryAccountInfoResult.class);
				
		return result;
	}

	@Override
	public String getApiName() {
		return "getAccountInfo";
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
		return ApiType.QUERYACCOUNTINFO;
	}

}
