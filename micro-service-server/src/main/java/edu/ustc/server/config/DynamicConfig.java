package edu.ustc.server.config;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@Configuration
public class DynamicConfig {
	
	private static final String DYNAMIC_CONFIG = "dynamicConfig";
	
	@Autowired
	private Environment environment;
	
	@PostConstruct
	public void init() {
		((AbstractEnvironment) environment).getPropertySources().addLast(new DynamicPropertySource(DYNAMIC_CONFIG));
	}
	
	public PropertySource<?> getDynamicPropertySources() {
		return ((AbstractEnvironment) environment).getPropertySources().get(DYNAMIC_CONFIG);
	}
	
	public int getPreDayRegisterQuantity() {
		return (Integer) getDynamicPropertySources().getProperty("person.register.pre.day");
	}
	
	public int getTotalRegisterQuantity() {
		return (Integer) getDynamicPropertySources().getProperty("person.register.total");
	}
	
	static class DynamicPropertySource extends MapPropertySource {
		
		private static Logger logger = LoggerFactory.getLogger(DynamicPropertySource.class);
		
		private static Map<String, Object> map = new ConcurrentHashMap<String, Object>();
		
		private static ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		
		static {
			service.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					map = dynamicLoadProperties();
				}
			}, 5, 10, TimeUnit.SECONDS);
		}
		
		public DynamicPropertySource(String name) {
			super(name, map);
		}
		
		@Override
		public Object getProperty(String name) {
			return map.get(name);
		}
		
		private static Map<String, Object> dynamicLoadProperties() {
			
			try {
				Properties properties = PropertiesLoaderUtils.loadAllProperties("application.properties");
				
				map.put("person.register.pre.day", Integer.parseInt(properties.getProperty("person.register.pre.day")));
				map.put("person.register.total", Integer.parseInt(properties.getProperty("person.register.total")));
				
				logger.info("load Properties result: {}", map);
			} catch (IOException e) {
				logger.error("load Properties error, {}", e.getMessage());
			}
			
			return map;
		}
	}
}
