package net.kadirderer.btc.db.repository;

import net.kadirderer.btc.db.model.BtcPlatform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BtcPlatformRepository extends JpaRepository<BtcPlatform, Integer> {
	
	@Query("Select bp from BtcPlatform bp where bp.code = :code")
	public BtcPlatform queryByCode(@Param("code") String code);

}
