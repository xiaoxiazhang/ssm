package com.alibaba.alisonar.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.alibaba.alisonar.dao.provider.AuthUserDynaSqlProvider;
import com.alibaba.alisonar.domain.AuthUser;
import com.alibaba.alisonar.dto.AuthUserDTO;
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

	@Select("select * from auth_user where username=#{username}")
	@ResultMap("BaseResultMap")
	AuthUser findByUsername(String username);


	List<AuthUser> findProvidedUser(AuthUserSearch search);

	Integer findProvidedUserCount(AuthUserSearch search);

	@Select("select c.role from auth_user a join auth_user_role b on a.id=b.auth_user_id join auth_role c  on b.auth_role_id=c.id "
			+ "where c.is_deleted=0 and a.username=#{username}")
	Set<String> findRoles(String username);

	@Select("select d.permission from auth_user a "
			+ "join auth_user_role b on a.id=b.auth_user_id "
			+ "join auth_role_permission c on c.auth_role_id=b.auth_role_id "
			+ "join auth_permission d on d.id=c.auth_permission_id "
			+ "where d.is_deleted=0 and a.username=#{username}")
	Set<String> findPermissions(String username);

	@Update("update auth_user a set a.is_deleted=1 where a.id=#{id}")
	void deleteUser(Long id);

	
	@SelectProvider(type=AuthUserDynaSqlProvider.class,method="listAuthUserSql")
	List<AuthUserDTO> listAuthUser(AuthUserDTO authUserDTO);

	@Update("update auth_user a set a.password=#{password} where a.id=#{id}")
	void updateUserPassword(@Param("id")Long id ,@Param("Password")String password);
	
	
}