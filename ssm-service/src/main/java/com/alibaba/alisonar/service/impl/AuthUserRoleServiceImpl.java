/**
 * 
 */
package com.alibaba.alisonar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.alisonar.dao.AuthUserRoleMapper;
import com.alibaba.alisonar.domain.AuthUserRole;
import com.alibaba.alisonar.service.AuthUserRoleService;

/**
 * @author wb-zxx263018
 *
 */

@Service
public class AuthUserRoleServiceImpl implements AuthUserRoleService {

	@Autowired
	private AuthUserRoleMapper authUserRoleMapper;
	
	
	@Override
	public int insertSelective(AuthUserRole record) {
		return authUserRoleMapper.insertSelective(record);
	}

	
	@Override
	public AuthUserRole selectByPrimaryKey(Long id) {
		return authUserRoleMapper.selectByPrimaryKey(id);
	}

	
	@Override
	public int updateByPrimaryKeySelective(AuthUserRole record) {
		return authUserRoleMapper.updateByPrimaryKeySelective(record);
	}

	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return authUserRoleMapper.deleteByPrimaryKey(id);
	}

}
