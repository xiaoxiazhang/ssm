/**
 * 
 */
package com.alibaba.alisonar.dto;

import java.io.Serializable;

/**
 * @author wb-zxx263018
 *
 */
public class UserLoginDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;

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

	@Override
	public String toString() {
		return "UserLoginDTO [username=" + username + ", password=" + password + "]";
	}
	
	

}
