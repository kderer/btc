package net.kadirderer.btc.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_USERACCESSKEY")
public class UserAccessKey {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "KEYTYPE")
	private char keyType;
	
	@Column(name = "KEYVALUE")
	private String keyValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public char getKeyType() {
		return keyType;
	}

	public void setKeyType(char keyType) {
		this.keyType = keyType;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}	

}
