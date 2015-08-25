package net.kadirderer.btc.db.dao;

import net.kadirderer.btc.db.model.ExchangeRates;

public interface ExchangeRatesDao {
	
	public ExchangeRates insert(ExchangeRates exhExchangeRates);
	
	public ExchangeRates queryByRecordGroup(Integer groupId);
	
	public ExchangeRates queryLatest();
	
}
