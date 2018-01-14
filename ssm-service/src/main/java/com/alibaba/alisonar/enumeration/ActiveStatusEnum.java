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
public enum ActiveStatusEnum {

	CLOSED(0, "未启用")  , USED(1, "启用");

	private Integer code;
	private String value;
	
	private static final Logger logger = LoggerFactory.getLogger(ActiveStatusEnum.class);

	private ActiveStatusEnum(Integer code, String value) {
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

	public static String getValueByCode(Integer code) {
		for (ActiveStatusEnum e : ActiveStatusEnum.values()) {
			if (e.getCode().equals(code)) {
				return e.getValue();
			}
		}
		logger.warn("ActiveStatusEnum中不存在该code值===>{}", code);
		return null;
	}

}
