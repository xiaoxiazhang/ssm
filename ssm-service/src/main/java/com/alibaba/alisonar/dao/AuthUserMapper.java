package com.alibaba.alisonar.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.alibaba.alisonar.domain.AuthUser;
import com.alibaba.alisonar.dto.AuthUserSearch;

public interface AuthUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuthUser record);

    int insertSelective(AuthUser record);

    AuthUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuthUser record);

    int updateByPrimaryKey(AuthUser record);
    
    @Select("select * from auth_user")
	@ResultMap("BaseResultMap")
	List<AuthUser> findAll();

	@Select("select * from auth_user where username=#{username} and is_deleted=0")
	@ResultMap("BaseResultMap")
	AuthUser findByUsername(String username);


	List<AuthUser> findProvidedUser(AuthUserSearch search);


	Integer findProvidedUserCount(AuthUserSearch search);	
}