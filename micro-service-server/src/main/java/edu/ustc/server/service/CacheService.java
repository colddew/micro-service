package edu.ustc.server.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

@Service
public class CacheService {
	
	private static final Logger logger = LoggerFactory.getLogger(CacheService.class);
	
	private static LoadingCache<String, String> caffeineCache;
	
	@PostConstruct
	public void init() {
		
		caffeineCache = Caffeine.newBuilder()
			.maximumSize(10_000)
			.expireAfterWrite(60, TimeUnit.SECONDS)
			.refreshAfterWrite(30, TimeUnit.SECONDS)
			.build(key -> costExpensiveResources(key));
		
		caffeineCache.put("k1", costExpensiveResources("k1"));
	}
	
	@Scheduled(fixedDelay = 1000 * 5)
	public void loadCaffeineCache() {
		logger.info("load caffeine cache, {}", caffeineCache.asMap());
	}

	private String costExpensiveResources(String key) {
		return RandomStringUtils.randomAlphanumeric(10);
	}
}
