package net.kadirderer.btc.db.repository;

import java.util.List;

import net.kadirderer.btc.db.model.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
	
	@Query("Select role from UserRole role where role.user.username = :username")
	public List<UserRole> findByUserName(@Param("username") String username);
}
