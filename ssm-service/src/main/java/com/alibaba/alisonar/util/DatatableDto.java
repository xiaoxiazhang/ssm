package com.alibaba.alisonar.util;

import java.io.Serializable;
import java.util.List;

public class DatatableDto<T> implements Serializable{
	
	private static final long serialVersionUID = -3331686649021065001L;

	// 总页数
	private Integer total;

	private List<T> rows;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
