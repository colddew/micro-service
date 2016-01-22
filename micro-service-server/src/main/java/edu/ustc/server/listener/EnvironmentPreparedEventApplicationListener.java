package edu.ustc.server.listener;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

public class EnvironmentPreparedEventApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
	
	private Logger logger = LoggerFactory.getLogger(EnvironmentPreparedEventApplicationListener.class);
	
	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		
		ConfigurableEnvironment environment = event.getEnvironment();
		
		for(Iterator<PropertySource<?>> it = environment.getPropertySources().iterator();it.hasNext();) {
			PropertySource<?> propertySource = it.next();
			getPropertiesFromSource(propertySource);
		}
		
		logger.info("2 Enviroment准备完毕,  EnvironmentPreparedEventApplicationListener...");
	}
	
	private void getPropertiesFromSource(PropertySource<?> propertySource) {
    	
        if (propertySource instanceof MapPropertySource) {
            for (String key : ((MapPropertySource) propertySource).getPropertyNames()) {
            	logger.info("{} : {}", key, propertySource.getProperty(key));
            }
        }
        
        if (propertySource instanceof CompositePropertySource) {
            for (PropertySource<?> s : ((CompositePropertySource) propertySource).getPropertySources()) {
                getPropertiesFromSource(s);
            }
        }
    }
}
