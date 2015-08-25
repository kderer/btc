package net.kadirderer.btc.db.repository;

import java.math.BigInteger;

import net.kadirderer.btc.db.model.AskBidRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AskBidRecordRepository extends JpaRepository<AskBidRecord, BigInteger> {
	
	@Query(value = "select F_GET_RECORGROUP_ID()", nativeQuery = true)
	public Integer obtainRecordGroupId();

}
