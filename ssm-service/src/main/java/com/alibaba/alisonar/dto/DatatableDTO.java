package com.alibaba.alisonar.dto;

import java.io.Serializable;
import java.util.List;

public class DatatableDTO<T> implements Serializable{
	
	private static final long serialVersionUID = -3331686649021065001L;

	// 总页数
	private long total;

	private List<T> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	

}
