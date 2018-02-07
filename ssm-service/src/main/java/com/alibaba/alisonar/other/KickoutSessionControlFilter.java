/**
 * 
 */
package com.alibaba.alisonar.other;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wb-zxx263018
 *
 */
@Component
public class KickoutSessionControlFilter extends AccessControlFilter {

	private static final Logger logger = LoggerFactory.getLogger(KickoutSessionControlFilter.class);

	private static final String kickoutUrl = "/login"; // 踢出后到的地址

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private RedisTemplate<Object, Object> jdkRedisTemplate;

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if (!subject.isAuthenticated() && !subject.isRemembered()) {
			// 如果没有登录，直接进行之后的流程
			return true;
		}

		Session session = subject.getSession();
		String username = (String) subject.getPrincipal();
		Serializable sessionId = session.getId();

		// 查看登录用户sessionID
		Serializable loginSessionId = (Serializable) jdkRedisTemplate.opsForValue().get(username);
		if (sessionId != loginSessionId) { //登录为新用户重新设置对应关系
			jdkRedisTemplate.opsForValue().set(username, sessionId);
		}

		//老用户设置kickout属性
		if (loginSessionId != null && !loginSessionId.equals(sessionId)) { 
			Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(loginSessionId));
			if (kickoutSession != null) {
				kickoutSession.setAttribute("kickout", true);
			}
		}
		// 老用户访问请求，通过session中kickout属性判断用户是否被剔除
		if (session.getAttribute("kickout") != null) {
			// 会话被踢出了
			subject.logout();
			saveRequest(request);
			WebUtils.issueRedirect(request, response, kickoutUrl);
			return false;
		}
		return true;

	}

}
