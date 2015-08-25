package net.kadirderer.btc.db.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import net.kadirderer.btc.db.model.ExchangeRates;
import net.kadirderer.btc.db.model.RecordGroup;
import net.kadirderer.btc.db.repository.ExchangeRatesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRatesDaoImpl implements ExchangeRatesDao {
	
	@Autowired
	private ExchangeRatesRepository exchangeRatesRepository;
	
	@Override
	public ExchangeRates insert(ExchangeRates exchangeRates) {
		exchangeRates.setRecordTime(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("GMT+8"))));
		return exchangeRatesRepository.save(exchangeRates);
	}

	@Override
	public ExchangeRates queryByRecordGroup(Integer groupId) {
		RecordGroup group = new RecordGroup();
		group.setRecordGroupId(groupId);
		return exchangeRatesRepository.queryByGroup(group);
	}

	@Override
	public ExchangeRates queryLatest() {
		return exchangeRatesRepository.queryLatest();
	}

}
