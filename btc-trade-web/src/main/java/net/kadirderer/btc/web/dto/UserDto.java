package net.kadirderer.btc.web.dto;

import java.util.Date;
import java.util.Set;

import net.kadirderer.btc.db.model.UserRole;

public class UserDto {
	
	private String username;
	private Date lastLoginTime;
	
	private Set<UserRole> roleSet;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Set<UserRole> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<UserRole> roleSet) {
		this.roleSet = roleSet;
	}	

}
