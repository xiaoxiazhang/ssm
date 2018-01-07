/**
 * 
 */
package com.alibaba.alisonar.configuration;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wb-zxx263018
 *
 */
@Aspect
@Component
public class TimeInterceptor {

	private static Logger logger = LoggerFactory.getLogger(TimeInterceptor.class);

	private static final long THRESHOLD = 2000; // 阈值

	@Around("execution (* com.alibaba.alisonar.service.impl.*.*(..))")
	public Object timeAround(ProceedingJoinPoint joinPoint) {
		// 定义返回对象、得到方法需要的参数
		Object obj = null;
		Object[] args = joinPoint.getArgs();
		long startTime = System.currentTimeMillis();

		try {
			obj = joinPoint.proceed(args);
		} catch (Throwable e) {
			logger.error("统计某方法执行耗时环绕通知出错", e);
		}

		// 获取执行的方法名
		long endTime = System.currentTimeMillis();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String methodName = signature.getDeclaringTypeName() + "." + signature.getName();

		// 打印耗时的信息
		this.printExecTime(methodName, startTime, endTime);
		return obj;
	}

	private void printExecTime(String methodName, long startTime, long endTime) {
		long diffTime = endTime - startTime;
		if (diffTime > THRESHOLD) {
			logger.warn("{}执行耗时:{}ms", methodName, diffTime);
		}
	}

}
