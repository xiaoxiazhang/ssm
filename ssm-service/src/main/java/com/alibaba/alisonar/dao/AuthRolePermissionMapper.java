package com.alibaba.alisonar.dao;

import com.alibaba.alisonar.domain.AuthRolePermission;

public interface AuthRolePermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuthRolePermission record);

    int insertSelective(AuthRolePermission record);

    AuthRolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuthRolePermission record);

    int updateByPrimaryKey(AuthRolePermission record);
}