/**
 * 
 */
package com.alibaba.alisonar.service;

import java.util.LinkedHashMap;

/**
 * @author wb-zxx263018
 *
 */
public interface FilterChainDefinitionsService {

	public LinkedHashMap<String, String> buildFilterChainDefinitionMap();

	public void reloadFilterChains();

}
