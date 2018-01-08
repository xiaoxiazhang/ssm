/**
 * 
 */
package com.alibaba.alisonar.factory;

import java.util.LinkedHashMap;

/**
 * @author wb-zxx263018
 *
 */
public class FilterChainDefinitionMapBuilder {
	public LinkedHashMap< String, String> buildFilterChainDefinitionMap(){
		LinkedHashMap< String, String> map = new LinkedHashMap<>();
		map.put("/bootstrap/**", "anon");
		map.put("/plugins/**", "anon");
		map.put("/assets/**", "anon");
		map.put("/dist/**", "anon");
		map.put("/login", "anon");
		map.put("/doLogin", "anon");
		map.put("/logout", "logout");
		map.put("/**", "authc");
		return map;
	}

}
