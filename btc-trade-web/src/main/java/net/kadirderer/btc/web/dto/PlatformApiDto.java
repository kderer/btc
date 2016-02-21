package net.kadirderer.btc.web.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class PlatformApiDto {
	
	private Integer id;	
	
	@NotEmpty
	private String type;
	
	@NotEmpty
	private String url;
	
	@NotEmpty
	private String returnType;
	
	private Integer platformId;	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

}
