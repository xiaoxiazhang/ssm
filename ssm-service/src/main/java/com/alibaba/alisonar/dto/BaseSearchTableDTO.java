/**
 * 
 */
package com.alibaba.alisonar.dto;

/**
 * @author wb-zxx263018
 *
 */
public class BaseSearchTableDTO {
	
	private Integer limit;
	private Integer offset;
	private String sortName;
	private String sortOrder;
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	@Override
	public String toString() {
		return "BaseSearchTableDTO [limit=" + limit + ", offset=" + offset + ", sortName=" + sortName + ", sortOrder="
				+ sortOrder + "]";
	}
	
	

}
