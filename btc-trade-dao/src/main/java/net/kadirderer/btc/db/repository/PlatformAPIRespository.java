package net.kadirderer.btc.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.kadirderer.btc.db.model.PlatformAPI;
import net.kadirderer.btc.util.enumaration.ApiType;

public interface PlatformAPIRespository extends JpaRepository<PlatformAPI, Integer> {
	
	@Query("Select pa from PlatformAPI pa where "
			+ "pa.platform.code = :platformCode and "
			+ "pa.type = :apiType")
	public PlatformAPI findByApiType(@Param("platformCode") String platformCode, @Param("apiType") ApiType apiType);

}
