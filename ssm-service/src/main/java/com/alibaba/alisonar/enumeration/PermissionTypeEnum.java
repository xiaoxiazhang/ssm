/**
 * 
 */
package com.alibaba.alisonar.enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wb-zxx263018
 *
 */
public enum PermissionTypeEnum {
	
	
	MENU_1(1,"一级菜单权限"),MEMU_2(2 , "二级菜单权限"),Button(3,"按钮权限");
	
	private Integer code;
	private String value;
	private static final Logger logger = LoggerFactory.getLogger(PermissionTypeEnum.class);
	
	private PermissionTypeEnum(Integer code, String value) {
		this.code = code;
		this.value = value;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValueByCode(Integer code){
		for(PermissionTypeEnum e : PermissionTypeEnum.values()){
			if(e.getCode().equals(code)){
				return e.getValue();
			}
		}
		logger.warn("PermissionType中不存在该code值===>{}",code);
		return null;
	}
	

	
	
	
	
	

}
