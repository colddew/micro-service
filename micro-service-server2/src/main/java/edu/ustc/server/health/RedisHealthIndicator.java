package edu.ustc.server.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

import edu.ustc.server.utils.RedisUtils;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;

@Component
public class RedisHealthIndicator extends AbstractHealthIndicator {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisHealthIndicator.class);
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Autowired
	private JedisPool jedisPool;
	
	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		
		JedisCommands jedisCommands = null;
		
		try {
			jedisCommands = RedisUtils.getJedisCommands(jedisCluster, jedisPool);
			logger.info("redis is working");
			builder.up().withDetail("description", "redis is working");
		} catch (Exception e) {
			logger.error("redis launch has error, {}", e.getMessage());
			builder.outOfService().withDetail("description", "redis launch has error, " + e.getMessage());
		} finally {
			if(null != jedisCommands) {
				RedisUtils.releaseJedisConnection(jedisCommands);
			}
		}
	}
}
