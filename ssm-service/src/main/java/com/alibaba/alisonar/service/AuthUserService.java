/**
 * 
 */
package com.alibaba.alisonar.service;

import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.alisonar.domain.AuthUser;
import com.alibaba.alisonar.dto.AuthUserSearch;

/**
 * @author wb-zxx263018
 *
 */
public interface AuthUserService extends BaseService<AuthUser> {
	
	
	AuthUser findByUsername(String username);
	
	List<AuthUser> findAll();

	List<AuthUser> findProvidedUser(AuthUserSearch search);

	Integer findProvidedUserCount(AuthUserSearch search);

	HSSFWorkbook buildExcelWorkBook(AuthUserSearch search);

	Set<String> findRoles(String username);

	Set<String> findPermissions(String username);

}
