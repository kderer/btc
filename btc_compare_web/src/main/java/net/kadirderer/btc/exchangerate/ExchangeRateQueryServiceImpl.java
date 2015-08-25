package net.kadirderer.btc.exchangerate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.kadirderer.btc.config.ApplicationConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExchangeRateQueryServiceImpl implements ExchangeRateQueryService {
	
	@Autowired
	private ApplicationConfig appConfig;

	@Override
	public ExchangeRatesTable getExchangeRateTable(String jsonString) {
		ObjectMapper om = new ObjectMapper();
		
		try {
			return om.readValue(jsonString, ExchangeRatesTable.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ExchangeRateQueryResult qetExchangeRates(ExchangeRateQuery obj, String jsonString) {
		ExchangeRatesTable erTable = getExchangeRateTable(jsonString);
		
		Double rate1 = erTable.getRates().get(obj.getFirstCurrency());
		Double rate2 = erTable.getRates().get(obj.getSecondCurrency());
		
		ExchangeRateQueryResult result = new ExchangeRateQueryResult();		
		result.setQuery(obj);
		result.setRateByFirst(rate2 / rate1);
		result.setRateBySecond(rate1 / rate2);
		
		return result;
	}
	
	public String queryExchangeRateJSON() {
		URL url;
		try {
			url = new URL(appConfig.getExchangeRateQueryUrl());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String input;
			StringBuffer json = new StringBuffer();

			while ((input = br.readLine()) != null) {
				json.append(input);
			}

			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ExchangeRatesTable changeBase(ExchangeRatesTable oldTable,
			String newBase) {
		
		ExchangeRatesTable newTable = new ExchangeRatesTable();		
		newTable.setBase(newBase);
		HashMap<String, Double> newRatesMap = new HashMap<String, Double>();	
		
		String[] selectedCurrencyList = appConfig.getSelectedCurrency().split("\\|");
		
		Map<String, Double> oldMap = oldTable.getRates();
		
		double divider = 1;
		
		if(!"EUR".equals(newBase)) {
			divider = oldTable.getRates().get(newBase);
			newRatesMap.put("EUR", (double)1 / divider);
		}
		
		for(String cur : selectedCurrencyList) {			
			if(!cur.equals(newBase) && oldMap.containsKey(cur)) {
				newRatesMap.put(cur, oldMap.get(cur) / divider);
			}
		}
		
		newTable.setRates(newRatesMap);
		return newTable;
	}

}
