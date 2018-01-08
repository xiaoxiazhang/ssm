package com.alibaba.alisonar.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.alibaba.alisonar.dao.provider.AuthPermissionDynaSqlProvider;
import com.alibaba.alisonar.domain.AuthPermission;
import com.alibaba.alisonar.dto.AuthPermissionDTO;

public interface AuthPermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuthPermission record);

    int insertSelective(AuthPermission record);

    AuthPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuthPermission record);

    int updateByPrimaryKey(AuthPermission record);

    @SelectProvider(type = AuthPermissionDynaSqlProvider.class, method = "listAuthPermissionSql")
	List<AuthPermissionDTO> listAuthPermission(AuthPermissionDTO authPermissionDTO);

    @SelectProvider(type = AuthPermissionDynaSqlProvider.class, method = "getCheckPermissionSql")
	List<AuthPermission> getCheckPermission(AuthPermissionDTO authPermissionDTO);

    @Update("update auth_permission a set a.is_deleted=1 where a.id = #{id}")
	void deletePermission(Long id);

    @Select("select * from auth_permission a where a.`level` in (1,2) and a.is_deleted=0")
    @ResultMap("BaseResultMap")
	List<AuthPermission> getAllParentNode();
}