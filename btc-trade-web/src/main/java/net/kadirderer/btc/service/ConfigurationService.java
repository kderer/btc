package net.kadirderer.btc.service;

import net.kadirderer.btc.db.model.Configuration;
import net.kadirderer.btc.web.dto.ConfigurationDto;
import net.kadirderer.btc.web.dto.DatatableAjaxResponse;

public interface ConfigurationService {
	
	public ConfigurationDto findByName(String name);
	
	public ConfigurationDto findById(int id);
	
	public DatatableAjaxResponse<Configuration> query(int pageSize, int pageNumber);
	
	public Configuration save(ConfigurationDto configDto);
	
	public void delete(int id);
}
