package com.alibaba.alisonar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.alibaba.alisonar.domain.Employee;
import com.alibaba.alisonar.dto.EmployeeDTO;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Integer id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);
    
    @Select("select a.id empId,a.emp_name empName,a.gender ,a.email,b.dept_name deptName from employee a "
			+ "join department b on b.id=a.dept_id where a.is_deleted=0 and b.is_deleted=0")
	List<EmployeeDTO> listEmpDTO();

    @Select("select count(*) from employee a where a.emp_name=#{empName}")
	int countEmpByname(String empName);

    @Select("select a.id empId,b.id deptId ,a.emp_name empName,a.gender ,a.email,b.dept_name deptName from employee a "
			+ "join department b on b.id=a.dept_id where a.is_deleted=0 and b.is_deleted=0 and a.id=#{id}")
	EmployeeDTO getEmpById(Integer id);
}