package net.kadirderer.btc.db.repository;

import java.util.List;

import net.kadirderer.btc.db.model.UserOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserOrderRepository extends JpaRepository<UserOrder, Integer> {
	
	@Query("Select uo from UserOrder uo where uo.username = :username and "
			+ "uo.orderType = :ordertype and "
			+ "uo.platformId = :platformId and "
			+ "uo.createDate = (select max(createDate) "
				+ "from UserOrder "
				+ "where username = :username and "
					+ "status = 'P' and "
					+ "orderType = :ordertype and "
					+ "platformId = :platformId)")
	public UserOrder findLastPending(@Param("username") String username, @Param("platformId") int platformId, @Param("ordertype") char ordertype);	
	
	@Query("Select uo from UserOrder uo where uo.username = :username")
	public List<UserOrder> findByUsername(@Param("username") String username);
	
	@Query("Select uo from UserOrder uo where uo.username = :username")
	public Page<UserOrder> findByUsername(@Param("username") String username, Pageable page);
	
	@Query("Select uo from UserOrder uo where uo.username = :username and uo.status = 'P' and platformId = :platformId")
	public List<UserOrder> findPending(@Param("username") String username, @Param("platformId") int platformId);
	
	@Query("Select uo from UserOrder uo where uo.username = :username and uo.status = 'P' and platformId = :platformId")
	public Page<UserOrder> findPending(@Param("username") String username,
			@Param("platformId") int platformId, Pageable page);
	
	@Query("Select uo from UserOrder uo where uo.username = :username and "
			+ "uo.platformId = :platformId and uo.returnId = :orderId")
	public UserOrder findByOrderId(@Param("username") String username, 
			@Param("platformId") int platformId, @Param("orderId") String orderId);
	
	@Query("Update UserOrder uo SET uo.profit = uo.profit + :profit WHERE "
			+ "uo.platformId = :platformId and uo.returnId = :orderId")
	@Modifying	
	public void addProfit(@Param("profit") Double profit, 
			@Param("platformId") int platformId, @Param("orderId") String orderId);

}
