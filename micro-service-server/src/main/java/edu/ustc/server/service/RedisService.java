package edu.ustc.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisCluster;

@Service
@EnableScheduling
public class RedisService {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Scheduled(fixedDelay = 1000 * 60 * 60)
	public void print() {
		logger.info(jedisCluster.toString());
	}
	
	public boolean lockUpdateOperation(String id) {
		
		Long setnx = jedisCluster.setnx(id, "");
		if (1 == setnx) {
			jedisCluster.expire(id, 60);
			return true;
		}
		
		return false;
	}
}
