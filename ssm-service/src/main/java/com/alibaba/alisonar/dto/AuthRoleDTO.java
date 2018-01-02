/**
 * 
 */
package com.alibaba.alisonar.dto;

/**
 * @author wb-zxx263018
 *
 */
public class AuthRoleDTO extends BaseSearchTableDTO {

	private Long id;

	private String role;

	private String description;

	private String permission;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

}
