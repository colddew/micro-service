package edu.ustc.server.config;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig extends SpringBootServletInitializer {

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