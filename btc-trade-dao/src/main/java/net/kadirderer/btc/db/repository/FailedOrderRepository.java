package net.kadirderer.btc.db.repository;

import java.util.Date;

import net.kadirderer.btc.db.model.FailedSellOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FailedOrderRepository extends JpaRepository<FailedSellOrder, Long> {
	
	@Query("Select fo from FailedSellOrder fo "
			+ "where fo.id = (select min(id) "
			+ "						from FailedSellOrder "
			+ "						where status = 'P' "
			+ "								and fo.createDate < :earlierThan )")
	public FailedSellOrder findFirstPending(@Param("earlierThan") Date earlierThan);

}
