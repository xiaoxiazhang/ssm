/**
 * 
 */
package com.alibaba.alisonar.service;

import java.util.List;

import com.alibaba.alisonar.domain.CodeRepository;

/**
 * @author wb-zxx263018
 *
 */
public interface CodeRepositoryService {
	
	CodeRepository saveCodeRepository(CodeRepository codeRepository);
	
	void updateCodeRepository(CodeRepository codeRepository);
	
	CodeRepository findOne(Integer id);
	
	List<CodeRepository> findAll();

}
