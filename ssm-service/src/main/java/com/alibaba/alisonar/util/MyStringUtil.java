/**
 * 
 */
package com.alibaba.alisonar.util;

/**
 * @author wb-zxx263018
 *
 */
public class MyStringUtil {

	/**
	 * @param 驼峰式字符串.
	 * @return 下划线字符串.
	 */
	public static String underscoreName(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			result.append(name.substring(0, 1).toLowerCase());
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
					result.append("_");
				}
				result.append(s.toLowerCase());
			}
		}
		return result.toString();
	}

	/**
	 * 将带'-'字符串改成驼峰式
	 * 
	 * @param 带'-'字符串
	 * @return 返回驼峰式字符串.
	 */
	public static String camelName(String name) {
		StringBuilder result = new StringBuilder();
		if (name == null || name.isEmpty()) {
			return "";
		}
		if (!name.contains("_")) {
			return name.substring(0, 1).toLowerCase() + name.substring(1);
			
		}
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for (String camel : camels) {
			if (camel.isEmpty()) {
				continue;
			}
			if (result.length() == 0) {
				result.append(camel.toLowerCase());
				
			} else {
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

}
