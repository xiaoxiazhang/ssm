/**
 * 
 */
package com.alibaba.alisonar.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.alisonar.dao.CodeRepositoryMapper;
import com.alibaba.alisonar.domain.CodeRepository;
import com.alibaba.alisonar.service.CodeRepositoryService;

/**
 * @author wb-zxx263018
 *
 */
@Service
@Transactional
public class CodeRepositoryServiceImpl implements CodeRepositoryService {

	private final static Logger logger = LoggerFactory.getLogger(CodeRepositoryServiceImpl.class);

	@Autowired
	private CodeRepositoryMapper codeRepositoryMapper;

	@Override
	public CodeRepository saveCodeRepository(CodeRepository codeRepository) {
		codeRepositoryMapper.saveCodeRepository(codeRepository);
		return codeRepositoryMapper.findOne(codeRepository.getId());

	}

	@Override
	public void updateCodeRepository(CodeRepository codeRepository) {
		codeRepositoryMapper.updateCodeRepository(codeRepository);

	}

	@Override
	public CodeRepository findOne(Integer id) {
		return codeRepositoryMapper.findOne(id);

	}

	@Override
	public List<CodeRepository> findAll() {
		return codeRepositoryMapper.findAll();

	}

}
