package net.kadirderer.btc.db.model;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "T_RECORDGROUP")
public class RecordGroup {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RECORDGROUPID")
	private Integer recordGroupId;
	
	@Column(name = "RECORDTIME", nullable = false)
	private Timestamp recordTime;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="recordGroup")
	@Fetch(FetchMode.JOIN)
	@MapKey(name="platformId")
	private Map<Integer, AskBidRecord> askBidRecords;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy="recordGroup")
	@Fetch(FetchMode.JOIN)
	private ExchangeRates exchangeRates;
	
	
	public Integer getRecordGroupId() {
		return recordGroupId;
	}
	
	public void setRecordGroupId(Integer recordGroupId) {
		this.recordGroupId = recordGroupId;
	}
	
	public Timestamp getRecordTime() {
		return recordTime;
	}
	
	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

	public Map<Integer, AskBidRecord> getAskBidRecords() {
		return askBidRecords;
	}

	public void setAskBidRecords(Map<Integer, AskBidRecord> askBidRecords) {
		this.askBidRecords = askBidRecords;
	}

	public ExchangeRates getExchangeRates() {
		return exchangeRates;
	}

	public void setExchangeRates(ExchangeRates exchangeRates) {
		this.exchangeRates = exchangeRates;
	}

}
