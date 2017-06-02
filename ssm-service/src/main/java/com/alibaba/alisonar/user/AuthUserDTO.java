package com.alibaba.alisonar.user;

public class AuthUserDTO {

	private Integer id;

	private String username;

	private String password;

	private String email;

	private Integer auFlag;

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
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAuFlag() {
		return auFlag;
	}

	public void setAuFlag(Integer auFlag) {
		this.auFlag = auFlag;
	}

}
