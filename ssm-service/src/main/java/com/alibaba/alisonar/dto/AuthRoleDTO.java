/**
 * 
 */
package com.alibaba.alisonar.dto;

import java.util.List;

/**
 * @author wb-zxx263018
 *
 */
public class AuthRoleDTO extends BaseSearchTableDTO {

	private Long id;

	private String role;

	private String description;

	private String permission;
	
	private List<String> permissions;
	
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

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
	
	

}
