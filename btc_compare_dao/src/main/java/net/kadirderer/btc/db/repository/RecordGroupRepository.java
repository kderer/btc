package net.kadirderer.btc.db.repository;

import java.sql.Timestamp;
import java.util.List;

import net.kadirderer.btc.db.model.RecordGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecordGroupRepository extends JpaRepository<RecordGroup, Integer> {

	@Query("Select distinct rg from RecordGroup rg "
			+ "left join fetch rg.askBidRecords abr "
			+ "left join fetch rg.exchangeRates er "
			+ "where abr.platformId in :platformIdList "
				+ "and rg.recordTime > :start and rg.recordTime < :end "
				+ "and MOD(rg.recordGroupId,:mod) = 0")
	public List<RecordGroup> queryBetweenTime(@Param("start") Timestamp startTime, 
			@Param("end") Timestamp endTime, @Param("mod") Integer mod,
			@Param("platformIdList") List<Integer> platforIdList);
	
	@Query("Select count(rg) from RecordGroup rg ")
	public Long getAllRecordCount();
	
	@Query("Select count(rg) from RecordGroup rg "
			+ "where rg.recordTime > :start and rg.recordTime < :end")
	public Integer getRecordGroupCountBetweenTime(@Param("start") Timestamp startTime, @Param("end") Timestamp endTime);
	

	@Query("Select count(distinct rg) from RecordGroup rg "
			+ "left join rg.askBidRecords abr "
			+ "where rg.recordTime > :start and rg.recordTime < :end "
				+ "and abr.platformId in :platformIdList")
	public Integer getRecordGroupCountBetweenTime(@Param("start") Timestamp startTime, @Param("end") Timestamp endTime, 
			@Param("platformIdList") List<Integer> platforIdList);	
	
}
