package com.alibaba.alisonar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.alibaba.alisonar.dao.provider.AuthRoleDynaSqlProvider;
import com.alibaba.alisonar.domain.AuthPermission;
import com.alibaba.alisonar.domain.AuthRole;
import com.alibaba.alisonar.dto.AuthRoleDTO;

public interface AuthRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuthRole record);

    int insertSelective(AuthRole record);

    AuthRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuthRole record);

    int updateByPrimaryKey(AuthRole record);

    @SelectProvider(type=AuthRoleDynaSqlProvider.class,method="listAuthRoleSql")
	List<AuthRoleDTO> listAuthRole(AuthRoleDTO authRoleDTO);

    @SelectProvider(type=AuthRoleDynaSqlProvider.class,method="getCheckRoleSql")
	List<AuthRole> getCheckPermission(AuthRoleDTO authRoleDTO);

	@Update("update auth_role a set a.is_deleted=1  where a.id=#{id}")
	void deleteRole(Long id); //逻辑删除

	@Select("select * from auth_role a where a.is_deleted=0")
	List<AuthRole> findAll();
	
	@Select("select * from auth_role a where a.role=#{role} and a.is_deleted=0")
	AuthRole getAuthRoleByRole(String role);

	@Select("select c.permission from auth_role a join auth_role_permission b on a.id=b.auth_role_id join auth_permission c on b.auth_permission_id=c.id where a.id = 1 and a.is_deleted=0 and c.is_deleted=0")
	List<String> getPermissionByRoleId(Long id);
}