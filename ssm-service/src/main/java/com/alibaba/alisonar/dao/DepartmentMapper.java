package com.alibaba.alisonar.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.alibaba.alisonar.domain.Department;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    Department selectByPrimaryKey(Integer id);

    List<Department> selectAll();

    int updateByPrimaryKey(Department record);

    @Select("select * from department a where a.is_deleted=0")
    @ResultMap("BaseResultMap")
	List<Department> listAllDepts();
}