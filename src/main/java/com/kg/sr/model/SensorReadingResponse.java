package com.kg.sr.model;

import java.util.List;

public class SensorReadingResponse {
	private Integer page;
	private Integer page_size;
	private Long total;
	private List<SensorReading> data;
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPage_size() {
		return page_size;
	}
	public void setPage_size(Integer page_size) {
		this.page_size = page_size;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<SensorReading> getData() {
		return data;
	}
	public void setData(List<SensorReading> data) {
		this.data = data;
	}
}
