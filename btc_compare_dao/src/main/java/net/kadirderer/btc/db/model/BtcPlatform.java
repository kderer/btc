package net.kadirderer.btc.db.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "T_BTCPLATFORM")
public class BtcPlatform {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "CODE", nullable = false)
	private String code;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "HOME_URL", nullable = false)
	private String homeUrl;
	
	@Column(name = "CURRENCY", nullable = false)
	private String currency;
	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "platform")
	private List<PlatformAPI> apiList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHomeUrl() {
		return homeUrl;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public List<PlatformAPI> getApiList() {
		return apiList;
	}

	public void setApiList(List<PlatformAPI> apiSet) {
		this.apiList = apiSet;
	}
	
	
	
}
