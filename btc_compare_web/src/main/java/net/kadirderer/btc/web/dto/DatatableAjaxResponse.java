package net.kadirderer.btc.web.dto;

import java.util.List;

public class DatatableAjaxResponse<T> {
	
	private long recordsTotal;
	private long recordsFiltered;
	private int draw;
	
	private List<T> data;
	
	public long getRecordsTotal() {
		return recordsTotal;
	}
	
	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	
	public List<T> getData() {
		return data;
	}
	
	public void setData(List<T> data) {
		this.data = data;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int pageNumber) {
		this.draw = pageNumber;
	}
	
}
