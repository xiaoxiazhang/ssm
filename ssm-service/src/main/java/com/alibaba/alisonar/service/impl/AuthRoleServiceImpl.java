/**
 * 
 */
package com.alibaba.alisonar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.alisonar.dao.AuthRoleMapper;
import com.alibaba.alisonar.domain.AuthPermission;
import com.alibaba.alisonar.domain.AuthRole;
import com.alibaba.alisonar.dto.AuthPermissionDTO;
import com.alibaba.alisonar.dto.AuthRoleDTO;
import com.alibaba.alisonar.dto.DatatableDTO;
import com.alibaba.alisonar.mapstuct.AuthPermissionConventor;
import com.alibaba.alisonar.service.AuthPermissionService;
import com.alibaba.alisonar.service.AuthRoleService;
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

	@Autowired
	private AuthPermissionConventor authPermissionConventor;

	@Autowired
	private AuthPermissionService authPermissionService;

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
	public DatatableDTO<AuthRoleDTO> buildDatatableDto(AuthRoleDTO authRoleDTO) {
		DatatableDTO<AuthRoleDTO> resultDto = new DatatableDTO<AuthRoleDTO>();
		PageHelper.startPage(authRoleDTO.getOffset() / authRoleDTO.getLimit() + 1, authRoleDTO.getLimit());
		List<AuthRoleDTO> list = authRoleMapper.listAuthRole(authRoleDTO);
		PageInfo<AuthRoleDTO> page = new PageInfo(list);
		resultDto.setRows(page.getList());
		resultDto.setTotal(page.getTotal());
		return resultDto;
	}

	@Override
	public boolean checkRole(AuthRoleDTO authRoleDTO) {
		List<AuthRole> ars = authRoleMapper.getCheckPermission(authRoleDTO);
		if (ars != null && ars.size() > 0) {
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

	@Override
	public List<AuthPermissionDTO> getAllPermissionByRoleId(Long id) {
		// 获取该角色拥有的所有权限
		List<String> rolePermissions = authRoleMapper.getPermissionByRoleId(id);

		// 获取所有的一级节点
		List<AuthPermissionDTO> level1 = authPermissionConventor.entities2DTOs(authPermissionService.getLevel1());
		for (AuthPermissionDTO dto1 : level1) { // 一级菜单
			if (rolePermissions!=null && rolePermissions.contains(dto1.getPermission())) {
				dto1.setChecked(true);
			}
			List<AuthPermissionDTO> level2 = authPermissionConventor
					.entities2DTOs(authPermissionService.getAllChildNodes(dto1.getId()));
			dto1.setChildNodes(level2); 
			
			for(AuthPermissionDTO dto2 : level2){//二级菜单
				if (rolePermissions!=null && rolePermissions.contains(dto2.getPermission())) {
					dto2.setChecked(true);
				}
				
				List<AuthPermissionDTO> level3 = authPermissionConventor
						.entities2DTOs(authPermissionService.getAllChildNodes(dto2.getId()));
				dto2.setChildNodes(level3);
				for(AuthPermissionDTO dto3 : level3){//三级按钮
					if (rolePermissions!=null && rolePermissions.contains(dto3.getPermission())) {
						dto3.setChecked(true);
					}
				}
			}
		}
		return level1;
	}

}
