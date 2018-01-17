/**
 * 
 */
package com.alibaba.alisonar.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @author wb-zxx263018
 *
 */
@Configuration
@EnableCaching
@PropertySource("classpath:/env/env.properties")
public class SpringDataRedisConfig {
	@Autowired
	private Environment env;

	@Bean
	public JedisPoolConfig poolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(Integer.valueOf(env.getProperty("redis.maxIdle")));
		jedisPoolConfig.setMaxTotal(Integer.valueOf(env.getProperty("redis.maxIdle")));
		jedisPoolConfig.setMaxWaitMillis(Long.valueOf(env.getProperty("redis.maxWait")));
		jedisPoolConfig.setTestOnBorrow(Boolean.valueOf(env.getProperty("redis.testOnBorrow")));
		return jedisPoolConfig;
	}

	@Bean
	public JedisConnectionFactory connectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setPoolConfig(poolConfig());
		jedisConnectionFactory.setHostName(env.getProperty("redis.host"));
		jedisConnectionFactory.setPort(Integer.valueOf(env.getProperty("redis.port")));
		jedisConnectionFactory.setPassword(env.getProperty("redis.pass"));
		jedisConnectionFactory.setTimeout(Integer.valueOf(env.getProperty("redis.timeout")));
		return jedisConnectionFactory;
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate() {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL); 
		jackson2JsonRedisSerializer.setObjectMapper(om);
		RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
		template.setConnectionFactory(connectionFactory());
		template.setKeySerializer(jackson2JsonRedisSerializer);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setHashKeySerializer(jackson2JsonRedisSerializer);
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}
	
	@Bean
	public RedisTemplate<Object, Object> jdkRedisTemplate() {
		RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
		template.setConnectionFactory(connectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JdkSerializationRedisSerializer());
		template.setHashKeySerializer(new JdkSerializationRedisSerializer());
		template.setHashValueSerializer(new JdkSerializationRedisSerializer());
		template.afterPropertiesSet();
		return template;
	}
	
	@Bean
    public CacheManager cacheManager() {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
		cacheManager.setDefaultExpiration(1800);
		//订制魂村
		Map<String ,Long> expires = new HashMap<>();
		cacheManager.setExpires(expires);
        return cacheManager;
    }

}
