/**
 * 
 */
package com.alibaba.alisonar.dao.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.alibaba.alisonar.dto.AuthRoleDTO;
import com.alibaba.alisonar.dto.AuthUserDTO;

/**
 * @author wb-zxx263018
 *
 */
public class AuthUserDynaSqlProvider {
	
public String listAuthUserSql(AuthUserDTO authUserDTO){
		
		SQL sql = new SQL().SELECT("au.id,au.username,au.email,au.is_deleted isDeleted,date_format(au.gmt_create,'%Y-%m-%d') createDate, GROUP_CONCAT(ar.role) rolesDesc")
				.FROM("auth_user au").LEFT_OUTER_JOIN("auth_user_role aur ON au.id=aur.auth_user_id")
				.LEFT_OUTER_JOIN("auth_role ar ON ar.id=aur.auth_role_id AND ar.is_deleted=0");
		if(StringUtils.isNotBlank(authUserDTO.getUsername())){
			sql.WHERE("au.username like concat('%',#{username},'%')");
			
		}
		if(StringUtils.isNotBlank(authUserDTO.getEmail())){
			sql.WHERE("au.email like CONCAT('%',#{email},'%')");
			
		}
		if(authUserDTO.getIsDeleted() != null){
			sql.WHERE("au.is_deleted= #{isDeleted}");
			
		}
		if(StringUtils.isNotBlank(authUserDTO.getCsDate())){
			sql.WHERE("au.gmt_create >= #{csDate} ");
			
		}
		
		if(StringUtils.isNotBlank(authUserDTO.getCeDate())){
			sql.WHERE("au.gmt_create <= #{ceDate} ");
			
		}
		
		if(authUserDTO.getRoles()!=null && authUserDTO.getRoles().size()>0){
			authUserDTO.setRolesDesc(StringUtils.join(authUserDTO.getRoles(),","));
			sql.WHERE("ar.role in (#{rolesDesc}) ");
			
		}
		sql.GROUP_BY("au.username");
		return sql.toString();
	}
	

}
