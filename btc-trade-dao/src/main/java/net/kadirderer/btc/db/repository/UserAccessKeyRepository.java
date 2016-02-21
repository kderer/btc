package net.kadirderer.btc.db.repository;

import java.util.List;

import net.kadirderer.btc.db.model.UserAccessKey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserAccessKeyRepository extends JpaRepository<UserAccessKey, Integer> {
	
	@Query("Select key from UserAccessKey key where key.username = :username")
	public List<UserAccessKey> findByUsername(@Param("username") String username);

}
