/**
 * 
 */
package com.alibaba.alisonar.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.alisonar.domain.AuthUser;
import com.alibaba.alisonar.dto.AuthUserSearch;

/**
 * @author wb-zxx263018
 *
 */
public interface AuthUserService {
	
	AuthUser saveAuthUser(AuthUser authUser);
	
	void updateAuthUser(AuthUser authUser);
	
	AuthUser findByUsername(String username);
	
	List<AuthUser> findAll();
	
	AuthUser findOne(Integer id);

	List<AuthUser> findProvidedUser(AuthUserSearch search);

	Integer findProvidedUserCount(AuthUserSearch search);

	HSSFWorkbook buildExcelWorkBook(AuthUserSearch search);

}
