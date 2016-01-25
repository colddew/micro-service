package edu.ustc.server.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MicroServiceInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(MicroServiceInterceptor.class);
	
	@Pointcut("execution(* edu.ustc.server.controller..*Controller.*(..))")
	public void microController() {
		
	}
	
	@Around("microController()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		
		logger.info("doing before MicroServiceInterceptor pointcut...");
		Object object = point.proceed();
		logger.info("doing after MicroServiceInterceptor pointcut...");
		
		return object;
	}
}