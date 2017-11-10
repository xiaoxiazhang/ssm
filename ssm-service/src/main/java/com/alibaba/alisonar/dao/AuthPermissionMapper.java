package com.alibaba.alisonar.dao;

import com.alibaba.alisonar.domain.AuthPermission;

public interface AuthPermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuthPermission record);

    int insertSelective(AuthPermission record);

    AuthPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuthPermission record);

    int updateByPrimaryKey(AuthPermission record);
}