/**
 * 
 */
package com.alibaba.alisonar.service;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.alisonar.domain.AuthUser;
import com.alibaba.alisonar.dto.AuthUserDTO;
import com.alibaba.alisonar.dto.AuthUserSearch;
import com.alibaba.alisonar.dto.DatatableDTO;
import com.alibaba.alisonar.dto.ResultDTO;

/**
 * @author wb-zxx263018
 *
 */
public interface AuthUserService extends BaseService<AuthUser> {
	
	AuthUser findByUsername(String username);
	
	List<AuthUser> findAll();

	List<AuthUser> findProvidedUser(AuthUserSearch search);

	Integer findProvidedUserCount(AuthUserSearch search);

	Workbook buildExcelWorkBook(AuthUserDTO authUserDTO);

	Set<String> findRoles(String username);

	Set<String> findPermissions(String username);

	void deleteUser(Long id);

	DatatableDTO<AuthUserDTO> buildDatatableDto(AuthUserDTO authUserDTO);

	void saveUser(AuthUserDTO authUserDTO);

	void updateUser(AuthUserDTO authUserDTO);

	void resetPassword(Long id);

	ResultDTO<String> saveAuthUserByExcel(InputStream inputStream);
}
