/**
 * 
 */
package com.alibaba.alisonar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.alisonar.dao.AuthRoleMapper;
import com.alibaba.alisonar.domain.AuthRole;
import com.alibaba.alisonar.dto.AuthRoleDTO;
import com.alibaba.alisonar.service.AuthRoleService;
import com.alibaba.alisonar.util.DatatableDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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

	@Override
	public DatatableDto<AuthRoleDTO> buildDatatableDto(AuthRoleDTO authRoleDTO) {
		DatatableDto<AuthRoleDTO> resultDto = new DatatableDto<AuthRoleDTO>();
		PageHelper.startPage(authRoleDTO.getOffset()/authRoleDTO.getLimit()+1, authRoleDTO.getLimit());
		List<AuthRoleDTO> list = authRoleMapper.listAuthRole(authRoleDTO);
		PageInfo<AuthRoleDTO> page= new PageInfo(list);
		resultDto.setRows(page.getList());
		resultDto.setTotal(page.getTotal());
		return resultDto;
	}

	@Override
	public boolean checkRole(AuthRoleDTO authRoleDTO) {
		List<AuthRole> ars = authRoleMapper.getCheckPermission(authRoleDTO);
		if(ars != null && ars.size() > 0){
			return false;
		}
		return true;
	}

	@Override
	public void deleteRole(Long id) {
		authRoleMapper.deleteRole(id);
	}

	@Override
	public List<AuthRole> findAll() {
		return authRoleMapper.findAll();
	}

	@Override
	public AuthRole getAuthRoleByRole(String role) {
		return authRoleMapper.getAuthRoleByRole(role);
	}

	
	

}
