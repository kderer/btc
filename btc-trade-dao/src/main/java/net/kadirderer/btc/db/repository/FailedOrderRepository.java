package net.kadirderer.btc.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.kadirderer.btc.db.model.FailedOrder;

public interface FailedOrderRepository extends JpaRepository<FailedOrder, Long> {
	
	@Query("Select fo from FailedOrder fo where fo.userOrderId = :userorderid")
	public FailedOrder findByUserOrderId(@Param("userorderid") int userorderid);

}
