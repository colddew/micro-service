package edu.ustc.server.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class PreparedEventApplicationListener implements ApplicationListener<ApplicationPreparedEvent> {
	
	private Logger logger = LoggerFactory.getLogger(PreparedEventApplicationListener.class);
	
	@Override
	public void onApplicationEvent(ApplicationPreparedEvent event) {
		
		ConfigurableApplicationContext context = event.getApplicationContext();
		getApplicationContext(context);
		
		logger.info("3 上下文创建完成, PreparedEventApplicationListener...");
	}
	
	private void getApplicationContext(ConfigurableApplicationContext context) {
		logger.info("applicationName : {}", context.getApplicationName());
	}
}
