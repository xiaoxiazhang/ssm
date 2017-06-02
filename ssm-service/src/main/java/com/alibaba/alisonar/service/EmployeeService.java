/**
 * 
 */
package com.alibaba.alisonar.service;

import com.alibaba.alisonar.domain.Employee;
import com.alibaba.alisonar.dto.EmployeeDTO;
import com.github.pagehelper.PageInfo;

/**
 * @author wb-zxx263018
 *
 */
public interface EmployeeService {
	
	/**
	 * @param 页数.
	 * @return 员工列表集合.
	 */
	PageInfo<Employee>  listEmpDTOByPage(Integer pageNum);
	
	/**
	 * @param employee 员工信息.
	 * @return 返回插入后的员工(包含id)
	 */
	Employee saveEmp(Employee employee);

	/**
	 * @param empName 员工姓名.
	 * @return 员工名为empName的个数
	 */
	int countEmpByname(String empName);

	/**
	 * @param 员工id.
	 * @return 返回对应id的员工信息.
	 */
	EmployeeDTO getEmpById(Integer id);

	/**
	 * @param id 员工id.
	 * @return 返回员工Domain.
	 */
	Employee findOne(Integer id);

	/**
	 * @param employee 员工.
	 */
	void updateEmp(Employee employee);
	
	/**
	 * @param id 员工id.
	 */
	void deleteEmp(Integer id);

	/**
	 * @param ids id字符数组.
	 */
	void multiDeleteEmps(Integer[] ids);

	/**
	 * @param ids 员工id用,号拼接的字符
	 */
	void multiDeleteEmps2(String ids);
	

}
