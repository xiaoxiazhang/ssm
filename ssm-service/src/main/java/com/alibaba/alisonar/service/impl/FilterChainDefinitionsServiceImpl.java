package com.alibaba.alisonar.service.impl;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.alisonar.domain.AuthPermission;
import com.alibaba.alisonar.service.AuthPermissionService;
import com.alibaba.alisonar.service.FilterChainDefinitionsService;

@Service
public class FilterChainDefinitionsServiceImpl implements FilterChainDefinitionsService {
	
	
	private static final Logger logger = LoggerFactory.getLogger(FilterChainDefinitionsServiceImpl.class);
	
	public static final String PREMISSION_STRING = "authc,perms[\"{0}\"]";

	@Autowired
	private AuthPermissionService authPermissionService;
	
	@Autowired
	private ShiroFilterFactoryBean shiroFilterFactory;
	

	@Override
	public void reloadFilterChains() {
		synchronized (shiroFilterFactory) {   //强制同步，控制线程安全  
            AbstractShiroFilter shiroFilter = null;  
            try {  
                shiroFilter = (AbstractShiroFilter) shiroFilterFactory.getObject();  
  
                PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter  
                        .getFilterChainResolver();  
                // 过滤管理器  
                DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();  
                // 清除权限配置  
                manager.getFilterChains().clear();  
                shiroFilterFactory.getFilterChainDefinitionMap().clear();  
                // 重新设置权限  
                LinkedHashMap<String, String>  filterChainDefinitionMap= buildFilterChainDefinitionMap();
                shiroFilterFactory.setFilterChainDefinitionMap(filterChainDefinitionMap);
                
                //重新生成过滤链  
                if (!CollectionUtils.isEmpty(filterChainDefinitionMap)) {  
                    for (Map.Entry<String, String> chain : filterChainDefinitionMap.entrySet()) {
                        manager.createChain(chain.getKey(), chain.getValue().replace(" ", ""));
                    }
                }  
            } catch (Exception e) {  
                logger.error("重置权限失败",e); 
            }  
        }  

	}


	@Override
	public LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
		List<AuthPermission> aps = authPermissionService.getAllFilterPermission();
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/bootstrap/**", "anon");
		map.put("/plugins/**", "anon");
		map.put("/assets/**", "anon");
		map.put("/dist/**", "anon");
		map.put("/login", "anon");
		map.put("/doLogin", "anon");
		map.put("/logout", "anon");
		for (AuthPermission permission : aps) {
			if (StringUtils.isNotBlank(permission.getPermission()) && StringUtils.isNotBlank(permission.getPermUrl())) {
				map.put(permission.getPermUrl(), MessageFormat.format(PREMISSION_STRING, permission.getPermission()));
				logger.info("{}===>{}", permission.getPermUrl(),
						MessageFormat.format(PREMISSION_STRING, permission.getPermission()));
			}

		}
		map.put("/**", "authc");
		logger.info("FilterChainDefinitionMap===>{}", map);
		return map;
	}

}
