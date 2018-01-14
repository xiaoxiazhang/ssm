/**
 * 
 */
package com.alibaba.alisonar.dao.provider;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.alisonar.dto.AuthPermissionDTO;


/**
 * @author wb-zxx263018
 *
 */
public class AuthPermissionDynaSqlProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthPermissionDynaSqlProvider.class);
	
	public String listAuthPermissionSql(AuthPermissionDTO authPermissionDTO){
		SQL sql = new SQL().SELECT("id,permission,description,level,perm_url permUrl,parent_id parentId,order_num orderNum,menu_icon menuIcon,is_active isActive")
				.FROM("auth_permission ap").WHERE("ap.is_deleted =0");
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
		
		if(authPermissionDTO.getIsActive() != null){
			sql.WHERE("ap.is_active = #{isActive}");
		}
		//sql.OR();
		if(authPermissionDTO.getParentNodes() != null && authPermissionDTO.getParentNodes().size() > 0){
			//"<foreach item='item' index='index' collection='dates' open='(' separator=',' close=')'> #{item}</foreach>"
			String sqlFragment=" ap.parent_id  in (" +StringUtils.join(authPermissionDTO.getParentNodes() ,",") + ")";
			sql.WHERE(sqlFragment);
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
