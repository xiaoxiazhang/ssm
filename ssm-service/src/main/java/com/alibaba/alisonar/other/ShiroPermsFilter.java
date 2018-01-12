/**
 * 
 */
package com.alibaba.alisonar.other;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.stereotype.Component;

import com.alibaba.alisonar.factory.ResultDTOFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author wb-zxx263018
 *
 */
@Component("shiroPermsFilter")
public class ShiroPermsFilter extends PermissionsAuthorizationFilter {
	
	
	/*
	 * shiro认证perms资源失败后回调方法
	 */
	@Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String type = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(type)) { //如果是ajax返回指定格式数据
        	response.setCharacterEncoding("UTF-8");
        	response.setContentType("application/json");
        	ObjectMapper objectMapper = new ObjectMapper(); 
        	response.getWriter().write(objectMapper.writeValueAsString(ResultDTOFactory.toNack(401, "没有操作权限")));
        } else {//如果是普通请求进行重定向
        	
        	response.sendRedirect(request.getContextPath() + "/unauthorized");
        }
        return false;
    }
	
	

}
