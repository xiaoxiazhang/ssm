package com.alibaba.alisonar.dao;

import org.apache.ibatis.annotations.Delete;

import com.alibaba.alisonar.domain.AuthUserRole;

public interface AuthUserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuthUserRole record);

    int insertSelective(AuthUserRole record);

    AuthUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuthUserRole record);

    int updateByPrimaryKey(AuthUserRole record);

    @Delete("delete a from auth_user_role a where a.auth_user_id=#{userId}")
	void deleteByUserId(Long userId);

}