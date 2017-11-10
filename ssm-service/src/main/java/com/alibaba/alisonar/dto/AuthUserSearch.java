/**
 * 
 */
package com.alibaba.alisonar.dto;

/**
 * @author wb-zxx263018
 *
 */
public class AuthUserSearch {

	private String username;
	private String gmtCreate;
	private String gmtModified;
	private Integer limit;
	private Integer offset;
	private String sortName;
	private String sortOrder;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(String gmtModified) {
		this.gmtModified = gmtModified;
	}

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
		return "AuthUserSearch [username=" + username + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
				+ ", limit=" + limit + ", offset=" + offset + ", sortName=" + sortName + ", sortOrder=" + sortOrder
				+ "]";
	}

	

}
