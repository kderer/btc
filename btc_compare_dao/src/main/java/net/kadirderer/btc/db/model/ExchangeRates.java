package net.kadirderer.btc.db.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_EXCHANGERATES")
public class ExchangeRates {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
		
	@Column(name = "RATES_MAP", nullable = false)
	private String ratesMap;
	
	@Column(name = "RECORDTIME", nullable = false)
	private Timestamp recordTime;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECORDID", nullable = true)
	private RecordGroup recordGroup;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRatesMap() {
		return ratesMap;
	}

	public void setRatesMap(String ratesMap) {
		this.ratesMap = ratesMap;
	}

	public RecordGroup getRecordGroup() {
		return recordGroup;
	}

	public void setRecordGroup(RecordGroup recordGroup) {
		this.recordGroup = recordGroup;
	}

	public Timestamp getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}	

}
