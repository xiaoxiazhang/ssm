package com.alibaba.alisonar.util;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author wb-zxx263018
 *
 */
public class PasswordHelper {
	
	public static String encryptPassword(String algorithmName, int hashIterations, String password, String salt) {
		String result = new SimpleHash(algorithmName, password, salt, hashIterations).toHex();
		return result;
	}

	public static void main(String[] args) {
		System.out.println(encryptPassword("md5", 2, "123456", "admin"));
		System.out.println(encryptPassword("md5", 2, "123456", "admin")
				.equals("928bfd2577490322a6e19b793691467e"));

	}

}
