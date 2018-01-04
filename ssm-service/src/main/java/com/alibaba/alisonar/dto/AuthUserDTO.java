package com.alibaba.alisonar.dto;

import java.util.List;

public class AuthUserDTO extends BaseSearchTableDTO{

	private Long id;

	private String username;

	private String email;

	private Integer isDeleted;
	
	private String createDate;
	
	private String csDate; //创建开始时间
	
	private String ceDate; //创建结束时间
	
	private List<String> roles; //
	
	private String rolesDesc; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCsDate() {
		return csDate;
	}

	public void setCsDate(String csDate) {
		this.csDate = csDate;
	}

	public String getCeDate() {
		return ceDate;
	}

	public void setCeDate(String ceDate) {
		this.ceDate = ceDate;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getRolesDesc() {
		return rolesDesc;
	}

	public void setRolesDesc(String rolesDesc) {
		this.rolesDesc = rolesDesc;
	}
	
	

}
