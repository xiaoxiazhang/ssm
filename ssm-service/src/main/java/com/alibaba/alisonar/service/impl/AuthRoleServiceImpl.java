/**
 * 
 */
package com.alibaba.alisonar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.alisonar.dao.AuthRoleMapper;
import com.alibaba.alisonar.domain.AuthRole;
import com.alibaba.alisonar.service.AuthRoleService;

/**
 * @author wb-zxx263018
 *
 */
@Service
public class AuthRoleServiceImpl implements AuthRoleService {

	
	@Autowired
	private AuthRoleMapper authRoleMapper;

	@Override
	public int insertSelective(AuthRole record) {
		return authRoleMapper.insertSelective(record);
		
	}

	@Override
	public AuthRole selectByPrimaryKey(Long id) {
		return authRoleMapper.selectByPrimaryKey(id);
		
	}

	@Override
	public int updateByPrimaryKeySelective(AuthRole record) {
		return authRoleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return authRoleMapper.deleteByPrimaryKey(id);
	}
	
	

}
