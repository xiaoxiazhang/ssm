/**
 * 
 */
package com.alibaba.alisonar.service;

import java.util.List;

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

	List<AuthPermission> getAllParentNode();

	List<AuthPermission> getLevel1();

	List<AuthPermission> getChildNodes(Long id);

	List<AuthPermission> getAllFilterPermission();

	List<AuthPermissionDTO> getSelect2ParentNode(String searchStr);


}
