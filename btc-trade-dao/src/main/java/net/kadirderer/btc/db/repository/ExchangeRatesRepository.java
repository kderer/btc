package net.kadirderer.btc.db.repository;

import java.math.BigInteger;

import net.kadirderer.btc.db.model.ExchangeRates;
import net.kadirderer.btc.db.model.RecordGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExchangeRatesRepository extends JpaRepository<ExchangeRates, BigInteger> {
	
	@Query("Select er from ExchangeRates er where er.recordGroup = :recordGroup")
	public ExchangeRates queryByGroup(@Param("recordGroup") RecordGroup recordGroup);
	
	@Query("Select er from ExchangeRates er where er.id = (select max(id) from ExchangeRates)")
	public ExchangeRates queryLatest();
}
