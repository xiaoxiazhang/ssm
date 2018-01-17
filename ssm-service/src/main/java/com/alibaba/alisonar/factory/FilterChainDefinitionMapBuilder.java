/**
 * 
 */
package com.alibaba.alisonar.factory;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.alisonar.service.FilterChainDefinitionsService;

/**
 * @author wb-zxx263018
 *
 */
@Component
public class FilterChainDefinitionMapBuilder {

	@Autowired
	private FilterChainDefinitionsService filterChainDefinitionsService;

	public LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
		return filterChainDefinitionsService.buildFilterChainDefinitionMap();
	}

}
