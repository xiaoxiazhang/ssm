/**
 * 
 */
package com.alibaba.alisonar.other;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.alibaba.alisonar.factory.ResultDTOFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author wb-zxx263018
 *
 */
@Component("authcFilter")
public class authcFilter extends PassThruAuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		if (isLoginRequest(servletRequest, servletResponse)) {
			return true;
		} else {
			// 如果ajax请求
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			String type = request.getHeader("X-Requested-With");
			if ("XMLHttpRequest".equals(type)) { // 如果是ajax返回指定格式数据
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				ObjectMapper objectMapper = new ObjectMapper();
				response.getWriter().write(objectMapper.writeValueAsString(ResultDTOFactory.toNack(403, "认证失败")));
			}else{ //不是ajax请求直接返回登录页面
				saveRequestAndRedirectToLogin(servletRequest, servletResponse);
			}
			return false;
		}
	}

}
