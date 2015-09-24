package edu.ustc.server.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceMonitor {
	
	@Pointcut("execution(* edu.ustc.server.controller..*Controller.*(..))")
	public void microController() {
		
	}
	
	@Around("microController()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		
		System.out.println("doing before pointcut...");
		Object object = point.proceed();
		System.out.println("doing after pointcut...");
		
		return object;
	}
}