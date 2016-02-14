package edu.ustc.server.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

@Aspect
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
public class HystrixCommandInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(HystrixCommandInterceptor.class);
	
	@Around("execution(* edu.ustc.server.controller..*Controller.*(..))")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		
		logger.info("doing before HystrixCommandInterceptor pointcut...");
		
		Object object = wrapHystrixCommand(point).execute();
		
		logger.info("doing after HystrixCommandInterceptor pointcut...");
		
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
					logger.error("micro service throw exception, {}", throwable.getMessage());
					throw (Exception) throwable;
				}
			}
			
			@Override
			protected Object getFallback() {
				logger.warn("micro service does not work normally...");
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
