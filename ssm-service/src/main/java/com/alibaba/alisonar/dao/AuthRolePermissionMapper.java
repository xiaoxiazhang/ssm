package com.alibaba.alisonar.dao;

import com.alibaba.alisonar.domain.AuthRolePermission;
import com.alibaba.alisonar.domain.AuthRolePermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthRolePermissionMapper {
    long countByExample(AuthRolePermissionExample example);

    int deleteByExample(AuthRolePermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AuthRolePermission record);

    int insertSelective(AuthRolePermission record);

    List<AuthRolePermission> selectByExample(AuthRolePermissionExample example);

    AuthRolePermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AuthRolePermission record, @Param("example") AuthRolePermissionExample example);

    int updateByExample(@Param("record") AuthRolePermission record, @Param("example") AuthRolePermissionExample example);

    int updateByPrimaryKeySelective(AuthRolePermission record);

    int updateByPrimaryKey(AuthRolePermission record);
}