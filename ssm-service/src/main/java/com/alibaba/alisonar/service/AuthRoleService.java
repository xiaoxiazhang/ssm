/**
 * 
 */
package com.alibaba.alisonar.service;

import java.util.List;

import com.alibaba.alisonar.domain.AuthRole;
import com.alibaba.alisonar.dto.AuthPermissionDTO;
import com.alibaba.alisonar.dto.AuthRoleDTO;
import com.alibaba.alisonar.dto.DatatableDTO;

/**
 * @author wb-zxx263018
 *
 */
public interface AuthRoleService extends BaseService<AuthRole>{

	DatatableDTO<AuthRoleDTO> buildDatatableDto(AuthRoleDTO authRoleDTO);

	boolean checkRole(AuthRoleDTO authRoleDTO);

	void deleteRole(Long id);
	
	List<AuthRole> findAll();

	AuthRole getAuthRoleByRole(String role);

	List<AuthPermissionDTO> getAllPermissionByRoleId(Long id);



}
