/**
 * 
 */
package com.alibaba.alisonar.dao.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.alibaba.alisonar.dto.AuthRoleDTO;

/**
 * @author wb-zxx263018
 *
 */
public class AuthRoleDynaSqlProvider {
	
public String listAuthRoleSql(AuthRoleDTO authRoleDTO){
		
		SQL sql = new SQL().SELECT("ar.id,ar.role,ar.description").FROM("auth_role ar").WHERE("ar.is_deleted=0");
		String role = authRoleDTO.getRole();
		if(StringUtils.isNotBlank(role)){
			sql.WHERE("ar.role like CONCAT('%',#{role},'%')");
		}
		String description = authRoleDTO.getDescription();
		if(StringUtils.isNotBlank(description)){
			sql.WHERE("ap.description like CONCAT('%',#{description},'%')");
			
		}
		return sql.toString();
	}
	
	
	public String getCheckRoleSql(AuthRoleDTO authRoleDTO) {

		SQL sql = new SQL().SELECT("*").FROM("auth_Role ar").WHERE("ar.is_deleted =0");
		String role = authRoleDTO.getRole();
		if (StringUtils.isNotBlank(role)) {
			sql.WHERE("ar.role=#{role}" );
		}
		Long id =  authRoleDTO.getId();
		if (id != null) {
			sql.WHERE("ar.id <> #{id}");

		}
		return sql.toString();
	}

}
