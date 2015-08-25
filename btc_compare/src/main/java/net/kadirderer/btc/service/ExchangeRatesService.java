package net.kadirderer.btc.service;

import net.kadirderer.btc.db.model.ExchangeRates;
import net.kadirderer.btc.db.model.RecordGroup;

public interface ExchangeRatesService {
	
	public ExchangeRates queryExchangeRates();
	
	public ExchangeRates insertWithRecordGroup(RecordGroup group);

}
