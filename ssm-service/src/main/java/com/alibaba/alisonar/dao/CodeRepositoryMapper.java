package com.alibaba.alisonar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.alibaba.alisonar.domain.CodeRepository;

public interface CodeRepositoryMapper {
	
	@Insert("INSERT INTO code_repository(user_id, path, branch_name) VALUES (#{userId}, #{path}, #{branchName})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int saveCodeRepository(CodeRepository codeRepository);
	
	
	@Update("UPDATE code_repository SET user_id=#{userId}, path=#{path},branch_name=#{branchName} WHERE id=#{id}")
	void updateCodeRepository(CodeRepository codeRepository);
	
	
	@Select("select * from code_repository where id=#{id}")
	@ResultMap("BaseResultMap")
	CodeRepository findOne(Integer id);
	
	
	@Select("select * from code_repository")
	@ResultMap("BaseResultMap")
	List<CodeRepository> findAll();
}