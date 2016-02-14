package edu.ustc.server.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(value = Ordered.LOWEST_PRECEDENCE)
@Component
public class MicroServiceInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(MicroServiceInterceptor.class);
	
	@Around("execution(* edu.ustc.server.controller..*Controller.*(..))")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		
		logger.info("doing before MicroServiceInterceptor pointcut...");
		Object object = point.proceed();
		logger.info("doing after MicroServiceInterceptor pointcut...");
		
		return object;
	}
}