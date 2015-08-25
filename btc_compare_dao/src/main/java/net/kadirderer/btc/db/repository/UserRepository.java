package net.kadirderer.btc.db.repository;

import net.kadirderer.btc.db.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {
	
	@Query("Select user from User user where user.username = :username and user.password = :password")
	public User findUser(@Param("username") String username, @Param("password") String password);
	
	@Modifying
	@Query("Update User set lastLoginTime = CURRENT_TIMESTAMP where username = :username")	
	public void updateLastLogin(@Param("username") String username);

}
