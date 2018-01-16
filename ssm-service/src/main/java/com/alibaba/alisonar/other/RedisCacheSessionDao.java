package com.alibaba.alisonar.other;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisCacheSessionDao extends CachingSessionDAO {
	

	@Autowired
	private RedisTemplate<Serializable, Session> redisTemplate;
	

	@Override
	protected void doUpdate(Session session) {
		if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
			return; // 如果会话过期/停止 没必要再更新了
		}
		// 使用java序列化值
		redisTemplate.opsForValue().set(session.getId(), session);

	}

	@Override
	protected void doDelete(Session session) {
		if (session == null || session.getId() == null) {
			return;
		}
		redisTemplate.delete(session.getId());
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		// 使用java序列化值
		redisTemplate.opsForValue().set(sessionId, session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		return  redisTemplate.opsForValue().get(sessionId);
	}
}
