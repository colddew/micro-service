package edu.ustc.server.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

public class StartedEventApplicationListener implements ApplicationListener<ApplicationStartedEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(StartedEventApplicationListener.class);
	
	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		
		SpringApplication app = event.getSpringApplication();
        app.setBannerMode(Mode.OFF);
        
		logger.info("1 spring boot启动, StartedEventApplicationListener...");
	}
}
