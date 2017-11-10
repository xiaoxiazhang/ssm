/**
 * 
 */
package com.alibaba.alisonar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.alisonar.dao.AuthRolePermissionMapper;
import com.alibaba.alisonar.domain.AuthRolePermission;
import com.alibaba.alisonar.service.AuthRolePermissionService;

/**
 * @author wb-zxx263018
 *
 */

@Service
public class AuthRolePermissionServiceImpl implements AuthRolePermissionService {
	
	
	@Autowired
	private  AuthRolePermissionMapper authRolePermissionMapper;
	
	@Override
	public int insertSelective(AuthRolePermission record) {
		return authRolePermissionMapper.insertSelective(record);
	}

	
	@Override
	public AuthRolePermission selectByPrimaryKey(Long id) {
		return authRolePermissionMapper.selectByPrimaryKey(id);
	}

	
	@Override
	public int updateByPrimaryKeySelective(AuthRolePermission record) {
		return authRolePermissionMapper.updateByPrimaryKeySelective(record);
	}

	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return authRolePermissionMapper.deleteByPrimaryKey(id);
	}

}
