package edu.ustc.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import edu.ustc.server.utils.RedisUtils;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;

@Service
@EnableScheduling
public class RedisService {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Autowired
	private JedisPool jedisPool;
	
	@Scheduled(fixedDelay = 5000)
	public void print() {
		
		JedisCommands jedisCommands = null;
		
		try {
			jedisCommands = RedisUtils.getJedisCommands(jedisCluster, jedisPool);
			logger.info(jedisCommands.get("foo"));
		} finally {
			if(null != jedisCommands) {
				RedisUtils.releaseJedisConnection(jedisCommands);
			}
		}
	}
}
