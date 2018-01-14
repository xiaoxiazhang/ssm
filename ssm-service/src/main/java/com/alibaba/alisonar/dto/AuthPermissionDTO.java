/**
 * 
 */
package com.alibaba.alisonar.dto;

import java.util.List;

/**
 * @author wb-zxx263018
 *
 */
public class AuthPermissionDTO extends BaseSearchTableDTO{
	
	private Long id;

	private Long parentId;
	
    private String permission;

    private String description;
    
    private String permUrl;

    private Integer level;
    
    private String permissionLevel;
    
    private Integer orderNum;
    
    private String menuIcon;
    
    private Integer isActive;
    
    private String activeStatus;
    
    private List<Long> parentNodes;
    
    private Boolean checked;
    
    private List<AuthPermissionDTO> childNodes;
    
    private String searchStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPermUrl() {
		return permUrl;
	}

	public void setPermUrl(String permUrl) {
		this.permUrl = permUrl;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	

	public String getPermissionLevel() {
		return permissionLevel;
	}

	public void setPermissionLevel(String permissionLevel) {
		this.permissionLevel = permissionLevel;
	}

	public List<Long> getParentNodes() {
		return parentNodes;
	}

	public void setParentNodes(List<Long> parentNodes) {
		this.parentNodes = parentNodes;
	}
	

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public List<AuthPermissionDTO> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<AuthPermissionDTO> childNodes) {
		this.childNodes = childNodes;
	}
	

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getSearchStr() {
		return searchStr;
	}

	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}

	@Override
	public String toString() {
		return "AuthPermissionDTO [id=" + id + ", permission=" + permission + ", description=" + description
				+ ", permUrl=" + permUrl + ", level=" + level + ", parentNodes=" + parentNodes + ", getLimit()="
				+ getLimit() + ", getOffset()=" + getOffset() + ", getSortName()=" + getSortName() + ", getSortOrder()="
				+ getSortOrder() + "]";
	}
    

}
