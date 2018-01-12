/**
 * 
 */
package com.alibaba.alisonar.factory;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.alisonar.domain.AuthPermission;
import com.alibaba.alisonar.service.AuthPermissionService;
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
