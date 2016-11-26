package net.kadirderer.btc.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kadirderer.btc.db.model.Configuration;

public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {
	
	public Configuration findByName(String name);

}
