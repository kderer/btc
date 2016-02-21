package net.kadirderer.btc.web.dto;

import java.util.List;

import net.kadirderer.btc.db.model.PlatformAPI;

import org.hibernate.validator.constraints.NotEmpty;

public class BtcPlatformDto {
	
	private Integer id;
	
	@NotEmpty
	private String code;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String currency;
	
	@NotEmpty
	private String url;
	
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
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public List<PlatformAPI> getApiList() {
		return apiList;
	}

	public void setApiList(List<PlatformAPI> apiList) {
		this.apiList = apiList;
	}

}
