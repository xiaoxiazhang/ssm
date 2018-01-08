/**
 * 
 */
package com.alibaba.alisonar.dao.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.alibaba.alisonar.dto.AuthPermissionDTO;

/**
 * @author wb-zxx263018
 *
 */
public class AuthPermissionDynaSqlProvider {
	
	public String listAuthPermissionSql(AuthPermissionDTO authPermissionDTO){
		
		SQL sql = new SQL().SELECT("id,permission,description,level,perm_url permUrl").FROM("auth_permission ap").WHERE("ap.is_deleted =0");
		String permission = authPermissionDTO.getPermission();
		if(StringUtils.isNotBlank(permission)){
			sql.WHERE("ap.permission = #{permission}");
		}
		String description = authPermissionDTO.getDescription();
		if(StringUtils.isNotBlank(description)){
			sql.WHERE("ap.description like CONCAT('%',#{description},'%')");
			
		}
		String permUrl = authPermissionDTO.getPermUrl();
		if(StringUtils.isNotBlank(permUrl)){
			sql.WHERE("ap.perm_url like CONCAT('%',#{permUrl},'%')");
		}
		
		if(authPermissionDTO.getLevel() != null){
			sql.WHERE("ap.level = #{level}");
		}
		if(authPermissionDTO.getParentNodes() != null && authPermissionDTO.getParentNodes().size() > 0){
			authPermissionDTO.setParentNodesStr(StringUtils.join(authPermissionDTO.getParentNodes() ,","));
			sql.WHERE("ap.parent_id in (#{parentNodesStr})");
		}
		
		return sql.toString();
	}
	
	
	public String getCheckPermissionSql(AuthPermissionDTO authPermissionDTO) {

		SQL sql = new SQL().SELECT("*").FROM("auth_permission ap").WHERE("ap.is_deleted =0");
		String permission = authPermissionDTO.getPermission();
		if (StringUtils.isNotBlank(permission)) {
			sql.WHERE("ap.permission=#{permission}" );
		}
		Long id =  authPermissionDTO.getId();
		if (id != null) {
			sql.WHERE("ap.id <> #{id}");

		}
		return sql.toString();
	}
	
	

}
