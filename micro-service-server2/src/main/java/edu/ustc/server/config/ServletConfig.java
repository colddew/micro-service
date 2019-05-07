package edu.ustc.server.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.alibaba.druid.support.http.StatViewServlet;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

@Configuration
public class ServletConfig extends SpringBootServletInitializer {
	
//	@SuppressWarnings("serial")
//	@Bean
//	public Servlet dispatcherServlet() {
//		return new GenericServlet() {
//			@Override
//			public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
//				res.setContentType("text/plain");
//				res.getWriter().append("Hello World");
//			}
//		};
//	}
	
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public ServletRegistrationBean statViewServlet() {
		StatViewServlet servlet = new StatViewServlet();
		ServletRegistrationBean bean = new ServletRegistrationBean(servlet, "/druid/*");
		return bean;
	}
	
	@Bean
	public ServletRegistrationBean hystrixMetricsStreamServlet() {
		HystrixMetricsStreamServlet servlet = new HystrixMetricsStreamServlet();
		ServletRegistrationBean bean = new ServletRegistrationBean(servlet, "/hystrix.stream");
		return bean;
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ServletConfig.class);
	}
}