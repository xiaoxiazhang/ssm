package com.alibaba.alisonar.other;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisCacheSessionDao extends CachingSessionDAO {
	
	
	@Autowired
	private  RedisTemplate<Object,Object>  jdkRedisTemplate;
	
	@Override
	protected void doUpdate(Session session) {
		if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
			return; // 如果会话过期/停止 没必要再更新了
		}
		// 使用java序列化值
		jdkRedisTemplate.opsForValue().set(session.getId(), session, 30 ,TimeUnit.MINUTES);

	}

	@Override
	protected void doDelete(Session session) {
		if (session == null || session.getId() == null) {
			return;
		}
		jdkRedisTemplate.delete(session.getId());
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		// 使用java序列化值
		jdkRedisTemplate.opsForValue().set(sessionId, session, 30 ,TimeUnit.MINUTES);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		return  (Session)jdkRedisTemplate.opsForValue().get(sessionId);
	}
}
