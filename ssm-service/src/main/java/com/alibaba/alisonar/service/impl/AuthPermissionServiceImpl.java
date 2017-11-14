/**
 * 
 */
package com.alibaba.alisonar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.alisonar.dao.AuthPermissionMapper;
import com.alibaba.alisonar.domain.AuthPermission;
import com.alibaba.alisonar.dto.AuthPermissionDTO;
import com.alibaba.alisonar.service.AuthPermissionService;
import com.alibaba.alisonar.util.DatatableDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author wb-zxx263018
 *
 */

@Service
public class AuthPermissionServiceImpl implements AuthPermissionService {

	@Autowired
	private AuthPermissionMapper authPermissionMapper;
	
	
	@Override
	public int insertSelective(AuthPermission record) {
		return authPermissionMapper.insertSelective(record);
	}

	
	@Override
	public AuthPermission selectByPrimaryKey(Long id) {
		return authPermissionMapper.selectByPrimaryKey(id);
		
	}

	@Override
	public int updateByPrimaryKeySelective(AuthPermission record) {
		return authPermissionMapper.updateByPrimaryKeySelective(record);
	}

	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return authPermissionMapper.deleteByPrimaryKey(id);
	}


	@Override
	public DatatableDto<AuthPermissionDTO> buildDatatableDto(AuthPermissionDTO authPermissionDTO) {
		DatatableDto<AuthPermissionDTO> resultDto = new DatatableDto<AuthPermissionDTO>();
		PageHelper.startPage(authPermissionDTO.getOffset()/authPermissionDTO.getLimit()+1, authPermissionDTO.getLimit());
		List<AuthPermissionDTO> list = authPermissionMapper.listAuthPermission(authPermissionDTO);
		PageInfo<AuthPermissionDTO> page= new PageInfo(list);
		resultDto.setRows(page.getList());
		resultDto.setTotal(page.getTotal());
		return resultDto;
	}


	@Override
	public boolean checkPermission(AuthPermissionDTO authPermissionDTO) {
		List<AuthPermission> aps = authPermissionMapper.getCheckPermission(authPermissionDTO);
		if(aps != null && aps.size() > 0){
			return false;
		}
		return true;
	}

	

}
