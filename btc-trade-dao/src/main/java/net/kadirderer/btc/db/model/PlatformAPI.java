package net.kadirderer.btc.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.kadirderer.btc.util.enumaration.ApiType;

@Entity
@Table(name = "T_PLATFORM_API")
public class PlatformAPI {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private ApiType type;
	
	@Column(name = "URL", nullable = false)
	private String url;
	
	@Column(name = "RETURNTYPE", nullable = false)
	private String returnType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLATFORMID", nullable = false)
	private BtcPlatform platform;	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	
	
	public ApiType getType() {
		return type;
	}

	public void setType(ApiType type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public BtcPlatform getPlatform() {
		return platform;
	}

	public void setPlatform(BtcPlatform platform) {
		this.platform = platform;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	
}
