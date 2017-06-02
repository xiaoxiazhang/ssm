package com.alibaba.alisonar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.alibaba.alisonar.domain.AuthUser;
import com.alibaba.alisonar.user.AuthUserSearch;

/**
 * @author wb-zxx263018
 *
 */
public interface AuthUserMapper {
	
	@Insert("INSERT INTO auth_user(username,email,password,salt,au_flag) VALUES(#{username},#{email},#{password},#{salt},#{auFlag})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int saveAuthUser(AuthUser authUser);
	
	
	@Update("UPDATE auth_user SET username=#{username},email=#{email}, password=#{password},salt=#{salt},au_flag=#{auFlag} WHERE id=#{id}")
	void updateAuthUser(AuthUser authUser);
	
	
	@Select("select * from auth_user where id=#{id}")
	@ResultMap("BaseResultMap")
	AuthUser findOne(Integer id);
	
	
	@Select("select * from auth_user")
	@ResultMap("BaseResultMap")
	List<AuthUser> findAll();

	@Select("select * from auth_user where username=#{username}")
	@ResultMap("BaseResultMap")
	AuthUser findByUsername(String username);


	List<AuthUser> findProvidedUser(AuthUserSearch search);


	Integer findProvidedUserCount(AuthUserSearch search);
	
	
}