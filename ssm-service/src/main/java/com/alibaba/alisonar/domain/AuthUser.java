package com.alibaba.alisonar.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author wb-zxx263018
 *
 */
public class AuthUser {
	private Integer id;

	private String username;

	private String password;

	private String email;

	private Integer auFlag;

	private String salt;
	
	

	public AuthUser() {
		super();
	}
	
	

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date gmtCreate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
	}

	public Integer getAuFlag() {
		return auFlag;
	}

	public void setAuFlag(Integer auFlag) {
		this.auFlag = auFlag;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	@Override
	public String toString() {
		return "AuthUser [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", auFlag=" + auFlag + ", salt=" + salt + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
				+ "]";
	}

	

}