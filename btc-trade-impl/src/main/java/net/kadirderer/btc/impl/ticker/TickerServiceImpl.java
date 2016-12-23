package net.kadirderer.btc.impl.ticker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.kadirderer.btc.api.ticker.TickerResult;
import net.kadirderer.btc.api.ticker.TickerService;
import net.kadirderer.btc.db.model.PlatformAPI;
import net.kadirderer.btc.util.enumaration.ApiType;

@Service
public class TickerServiceImpl implements TickerService {
	
	@Resource
	private Map<Integer, Map<ApiType, PlatformAPI>> platformApiMap;

	@Override
	public TickerResult getTicker(Integer platformId) throws Exception {
		PlatformAPI api = platformApiMap.get(platformId).get(ApiType.TICKER);
		
		String url = api.getUrl();
		
		if(url.startsWith("https")) {
			return queryHttps(api);
		}
		else {
			RestTemplate rt = new RestTemplate();			
			return (TickerResult)rt.getForObject(api.getUrl(), Class.forName(api.getReturnType()));
		}
	}

	private TickerResult queryHttps(PlatformAPI api) {
		URL url;
		
		TickerResult result = null;
		
		try {
			url = new URL(api.getUrl());
			
			result = (TickerResult)Class.forName(api.getReturnType()).newInstance();

			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

			con.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String string, SSLSession ssls) {
					return true;
				}
			});

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String input;
			StringBuffer json = new StringBuffer();

			while ((input = br.readLine()) != null) {
				json.append(input);
			}
			
			br.close();

			ObjectMapper om = new ObjectMapper();
			result = (TickerResult)om.readValue(json.toString(), Class.forName(api.getReturnType()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
