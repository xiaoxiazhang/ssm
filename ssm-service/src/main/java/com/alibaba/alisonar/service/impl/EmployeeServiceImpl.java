/**
 * 
 */
package com.alibaba.alisonar.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.alisonar.dao.EmployeeMapper;
import com.alibaba.alisonar.domain.Employee;
import com.alibaba.alisonar.dto.EmployeeDTO;
import com.alibaba.alisonar.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author wb-zxx263018
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	
	private static final Integer pageSize = 10;
	
	@Autowired
	private EmployeeMapper employeeMapper;

	@Override
	public PageInfo<Employee> listEmpDTOByPage(Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		List<EmployeeDTO> list = employeeMapper.listEmpDTO();
		PageInfo<Employee> page= new PageInfo(list);
		logger.info("emps===>",list);
		return page;
	}

	
	@Override
	@Transactional
	public Employee saveEmp(Employee employee) {
		employeeMapper.insert(employee);
		return employee;
	}


	@Override
	public int countEmpByname(String empName) {
		return employeeMapper.countEmpByname(empName);
	}


	@Override
	public EmployeeDTO getEmpById(Integer id) {
		return employeeMapper.getEmpById(id);
	}


	@Override
	public Employee findOne(Integer id) {
		return employeeMapper.selectByPrimaryKey(id);
		
	}


	
	@Override
	@Transactional
	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKey(employee);
		
	}


	
	@Override
	@Transactional
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
		
	}


	@Override
	@Transactional
	public void multiDeleteEmps(Integer[] ids) {
		for(Integer id : ids){
			employeeMapper.deleteByPrimaryKey(id);
		}
	}


	@Override
	@Transactional
	public void multiDeleteEmps2(String ids) {
		String [] strs = StringUtils.split(ids, ",");
		for(String id : strs){
			employeeMapper.deleteByPrimaryKey(Integer.valueOf(id));
		}
		
	}

}
