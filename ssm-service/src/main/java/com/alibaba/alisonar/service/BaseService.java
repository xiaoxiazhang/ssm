/**
 * 
 */
package com.alibaba.alisonar.service;

/**
 * @author wb-zxx263018
 *
 */
public  interface BaseService<T> {
	
	int insertSelective(T record);
	
	T selectByPrimaryKey(Long id);
	
	int updateByPrimaryKeySelective(T record);
	
	int deleteByPrimaryKey(Long id);
	
	

}
