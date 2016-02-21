package net.kadirderer.btc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.db.dao.ExchangeRatesDao;
import net.kadirderer.btc.db.model.ExchangeRates;
import net.kadirderer.btc.db.model.RecordGroup;
import net.kadirderer.exchangerate.query.ExchangeRateQueryService;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

	@Autowired
	private ExchangeRateQueryService exchangeRateQueryService;
	
	@Autowired
	private ExchangeRatesDao exchangeRatesDao;
	
	@Override
	public ExchangeRates queryExchangeRates() {
		String exchangeRatesJSON = exchangeRateQueryService.queryExchangeRateJSON();
		
		if (exchangeRatesJSON == null) {
			exchangeRatesJSON = exchangeRatesDao.queryLatest().getRatesMap();
		}
		
		ExchangeRates exchangeRates = new ExchangeRates();
		exchangeRates.setRatesMap(exchangeRatesJSON);
		
		return exchangeRates;
	}

	@Override
	public ExchangeRates insertWithRecordGroup(RecordGroup group) {
		ExchangeRates rates = queryExchangeRates();
		rates.setRecordGroup(group);
		
		return exchangeRatesDao.insert(rates);
	}

}
