package net.kadirderer.btc.db.dao;

import org.springframework.data.domain.Page;

import net.kadirderer.btc.db.model.Configuration;

public interface ConfigurationDao {
	
	public Configuration save(Configuration Configuration);
	
	public Page<Configuration> query(int pageSize, int pageNumber);
	
	public Configuration findByName(String name);
	
	public Configuration findById(int id);
	
	public void delete(int id);
	
}
