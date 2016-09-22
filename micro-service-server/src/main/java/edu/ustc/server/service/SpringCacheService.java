package edu.ustc.server.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SpringCacheService {
	
	private static final Logger logger = LoggerFactory.getLogger(CaffeineCacheService.class);
	
	@PostConstruct
	public void init() {
		
	}
	
	@Scheduled(fixedDelay = 1000 * 5)
	public void loadSpringCache() {
		logger.info("load spring cache");
	}
}
