package com.alibaba.alisonar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.alibaba.alisonar.dao.provider.AuthRoleDynaSqlProvider;
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
}