/**
 * 
 */
package com.alibaba.alisonar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.alisonar.domain.AuthPermission;
import com.alibaba.alisonar.service.AuthPermissionService;

/**
 * @author wb-zxx263018
 *
 */

@Service
public class AuthPermissionServiceImpl implements AuthPermissionService {

	@Autowired
	private AuthPermissionService authPermissionService;
	
	
	@Override
	public int insertSelective(AuthPermission record) {
		return authPermissionService.insertSelective(record);
	}

	
	@Override
	public AuthPermission selectByPrimaryKey(Long id) {
		return authPermissionService.selectByPrimaryKey(id);
		
	}

	@Override
	public int updateByPrimaryKeySelective(AuthPermission record) {
		return authPermissionService.updateByPrimaryKeySelective(record);
	}

	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return authPermissionService.deleteByPrimaryKey(id);
	}

}
