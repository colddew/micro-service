package edu.ustc.server.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;

public class FailedEventApplicationListener implements ApplicationListener<ApplicationFailedEvent> {
	
	private Logger logger = LoggerFactory.getLogger(FailedEventApplicationListener.class);
	
	@Override
	public void onApplicationEvent(ApplicationFailedEvent event) {
		
		Throwable exception = event.getException();
		handleException(exception);
		
		logger.info("4 处理异常, FailedEventApplicationListener...");
	}
	
	private void handleException(Throwable exception) {
		logger.info("exception : {}", exception.getMessage());
	}
}
