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
    
    private List<Long> parentNodes;
    
    private String parentNodesStr;

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

	public List<Long> getParentNodes() {
		return parentNodes;
	}

	public void setParentNodes(List<Long> parentNodes) {
		this.parentNodes = parentNodes;
	}
	
	

	public String getParentNodesStr() {
		return parentNodesStr;
	}

	public void setParentNodesStr(String parentNodesStr) {
		this.parentNodesStr = parentNodesStr;
	}
	

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "AuthPermissionDTO [id=" + id + ", permission=" + permission + ", description=" + description
				+ ", permUrl=" + permUrl + ", level=" + level + ", parentNodes=" + parentNodes + ", getLimit()="
				+ getLimit() + ", getOffset()=" + getOffset() + ", getSortName()=" + getSortName() + ", getSortOrder()="
				+ getSortOrder() + "]";
	}
    

}
