/**
 * 
 */
package com.alibaba.alisonar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.alisonar.dao.DepartmentMapper;
import com.alibaba.alisonar.domain.Department;
import com.alibaba.alisonar.service.DepartmentService;

/**
 * @author wb-zxx263018
 *
 */

@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	private DepartmentMapper departmentMapper;

	@Override
	public List<Department> listAllDepts() {
		return departmentMapper.listAllDepts();
	}

}
