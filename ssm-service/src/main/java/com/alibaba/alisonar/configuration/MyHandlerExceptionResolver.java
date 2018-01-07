/**
 * 
 */
package com.alibaba.alisonar.configuration;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wb-zxx263018
 *
 */
@Component
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
	
	/*
	 * 异常处理：
	 * #1.ajax请求异常由error函数处理
	 * #2.页面错误404,500
	 */

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj,
			Exception e) {
		
        String requestType = request.getHeader("X-Requested-With"); 
        if ("XMLHttpRequest".equals(requestType)) return null; //由ajax error回调函数处理
		if (e instanceof Exception) {
			ModelAndView modelAndView = new ModelAndView("error");
			modelAndView.addObject("msg", e.getMessage());
			return modelAndView;
		}  
        return null; 
	}
}
