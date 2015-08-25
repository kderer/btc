package net.kadirderer.btc.db.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_ASKBIDRECORD")
public class AskBidRecord {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "PLATFORMID", nullable = false)
	private Integer platformId;
	
	@Column(name = "ASK_RATE", nullable = false)
	private Double askRate;
	
	@Column(name = "BID_RATE", nullable = false)
	private Double bidRate;
	
	@Column(name = "RECORDDATE", nullable = false)
	private Timestamp recordate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECORD_GROUP", nullable = true)
	private RecordGroup recordGroup;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public Double getAskRate() {
		return askRate;
	}

	public void setAskRate(Double askRate) {
		this.askRate = askRate;
	}

	public Double getBidRate() {
		return bidRate;
	}

	public void setBidRate(Double bidRate) {
		this.bidRate = bidRate;
	}

	public Timestamp getRecordate() {
		return recordate;
	}

	public void setRecordate(Timestamp recordate) {
		this.recordate = recordate;
	}

	public RecordGroup getRecordGroup() {
		return recordGroup;
	}

	public void setRecordGroup(RecordGroup recordGroup) {
		this.recordGroup = recordGroup;
	}
	
}
