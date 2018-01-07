/**
 * 
 */
package com.alibaba.alisonar.service;

import com.alibaba.alisonar.domain.AuthPermission;
import com.alibaba.alisonar.dto.AuthPermissionDTO;
import com.alibaba.alisonar.dto.DatatableDTO;

/**
 * @author wb-zxx263018
 *
 */
public interface AuthPermissionService extends BaseService<AuthPermission> {

	DatatableDTO<AuthPermissionDTO> buildDatatableDto(AuthPermissionDTO authPermissionDTO);

	boolean checkPermission(AuthPermissionDTO authPermissionDTO);

	void deletePermission(Long id);


}
