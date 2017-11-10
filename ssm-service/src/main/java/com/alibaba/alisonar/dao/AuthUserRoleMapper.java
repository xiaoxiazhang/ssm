package com.alibaba.alisonar.dao;

import com.alibaba.alisonar.domain.AuthUserRole;

public interface AuthUserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuthUserRole record);

    int insertSelective(AuthUserRole record);

    AuthUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuthUserRole record);

    int updateByPrimaryKey(AuthUserRole record);
}