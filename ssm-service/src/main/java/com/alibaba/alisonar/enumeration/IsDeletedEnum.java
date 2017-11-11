package com.alibaba.alisonar.enumeration;

/**
 * @author wb-zxx263018
 *
 */
public enum IsDeletedEnum {
	YES(1), NO(0);
	private Integer code;

	private IsDeletedEnum(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
