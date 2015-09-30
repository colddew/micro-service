package edu.ustc.server.config;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;

@Configuration
public class InterceptorConfig {
	
	@Bean
	public DruidStatInterceptor druidStatInterceptor() {
		return new DruidStatInterceptor();
	}
	
	@Bean
	public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
		
		BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
		creator.setProxyTargetClass(true);
		creator.setBeanNames("classesController");
		creator.setInterceptorNames("druidStatInterceptor");
		
		System.out.println("load druid stat interceptor...");
		
		return creator;
	}
}
