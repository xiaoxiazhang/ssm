package com.alibaba.alisonar.dao;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

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
}