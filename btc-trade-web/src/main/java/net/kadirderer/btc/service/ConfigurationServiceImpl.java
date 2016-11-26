package net.kadirderer.btc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import net.kadirderer.btc.db.dao.ConfigurationDao;
import net.kadirderer.btc.db.model.Configuration;
import net.kadirderer.btc.web.dto.ConfigurationDto;
import net.kadirderer.btc.web.dto.DatatableAjaxResponse;

@Service
public class ConfigurationServiceImpl extends BaseDtoService<Configuration, ConfigurationDto> implements ConfigurationService {

	@Autowired
	private ConfigurationDao configurationDao;
	
	@Override
	public ConfigurationDto findByName(String name) {
		return createDto(configurationDao.findByName(name));
	}

	@Override
	public ConfigurationDto findById(int id) {
		return createDto(configurationDao.findById(id));
	}

	@Override
	public DatatableAjaxResponse<Configuration> query(int pageSize, int pageNumber) {
		Page<Configuration> pageResult = configurationDao.query(pageSize, pageNumber);
		
		DatatableAjaxResponse<Configuration> ajaxResponse = new DatatableAjaxResponse<>();
		ajaxResponse.setData(pageResult.getContent());
		ajaxResponse.setRecordsTotal(pageResult.getTotalElements());
		ajaxResponse.setRecordsFiltered(pageResult.getTotalElements());
		
		return ajaxResponse;
	}

	@Override
	public Configuration save(ConfigurationDto configDto) {
		return configurationDao.save(createModel(configDto));
	}

	@Override
	public void delete(int id) {
		configurationDao.delete(id);
	}
	

	@Override
	public void customCopyToDto(Configuration modelObject, ConfigurationDto dtoObject) {
		
	}

	@Override
	public void customCopyToModel(ConfigurationDto dtoObject, Configuration modelObject) {
		
	}
	
	
}
