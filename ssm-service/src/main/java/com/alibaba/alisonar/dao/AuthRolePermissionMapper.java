package com.alibaba.alisonar.dao;

import org.apache.ibatis.annotations.Delete;

import com.alibaba.alisonar.domain.AuthRolePermission;

public interface AuthRolePermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuthRolePermission record);

    int insertSelective(AuthRolePermission record);

    AuthRolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuthRolePermission record);

    int updateByPrimaryKey(AuthRolePermission record);

    @Delete("delete a from auth_role_permission a where a.auth_role_id=#{id} ")
	void deleteByRoleId(Long id);
}