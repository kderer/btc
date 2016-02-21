package net.kadirderer.btc.impl.queryaccountinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoResult;
import net.kadirderer.btc.api.queryaccountinfo.QueryAccountInfoService;
import net.kadirderer.btc.impl.util.btcc.BtcChinaApiCallable;
import net.kadirderer.btc.impl.util.btcc.BtcChinaPlatformClient;
import net.kadirderer.btc.util.enumaration.ApiType;

@Service
public class BtcChinaQueryAccountInfoService implements QueryAccountInfoService, BtcChinaApiCallable {
	
	@Autowired
	private BtcChinaPlatformClient btccClient;

	@Override
	@Transactional
	public QueryAccountInfoResult queryAccountInfo(String username) throws Exception {
		String jsonResult = btccClient.callApi(this, username);

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
