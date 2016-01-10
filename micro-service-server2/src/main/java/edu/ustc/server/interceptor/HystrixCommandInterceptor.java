package edu.ustc.server.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

@Aspect
@Component
public class HystrixCommandInterceptor {
	
	@Pointcut("execution(* edu.ustc.server.controller..*Controller.*(..))")
	public void microController() {
		
	}
	
	@Around("microController()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		
		System.out.println("doing before HystrixCommandInterceptor pointcut...");
		
		Object object = wrapHystrixCommand(point).execute();
		
		System.out.println("doing after HystrixCommandInterceptor pointcut...");
		
		return object;
	}
	
	private HystrixCommand<Object> wrapHystrixCommand(final ProceedingJoinPoint point) {
		
		String className = point.getTarget().getClass().getSimpleName();
		String methodName = point.getSignature().getName();
		Setter setter = configHystrixCommand(className, methodName);
		
		return new HystrixCommand<Object>(setter) {
			
			@Override
			protected Object run() throws Exception {
				try {
					return point.proceed();
				} catch (Throwable throwable) {
					throw (Exception) throwable;
				}
			}
			
			@Override
			protected Object getFallback() {
				return null;
			}
		};
	}
	
	private HystrixCommand.Setter configHystrixCommand(String className, String methodName) {
		return HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(className + "Group"))
				.andCommandKey(HystrixCommandKey.Factory.asKey(className + "." + methodName))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(className + "ThreadPool"))
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
				.withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD))
				.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(10));
	}
}
