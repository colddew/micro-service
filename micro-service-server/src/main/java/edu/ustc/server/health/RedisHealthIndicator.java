package edu.ustc.server.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCluster;

@Component
@ConditionalOnBean(JedisCluster.class)
public class RedisHealthIndicator extends AbstractHealthIndicator {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisHealthIndicator.class);
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		
		try {
			jedisCluster.get("foo");
			logger.info("redis is working");
			builder.up().withDetail("description", "redis is working");
		} catch (Exception e) {
			logger.error("redis launch has error, {}", e.getMessage());
			builder.outOfService().withDetail("description", "redis launch has error, " + e.getMessage());
		}
	}
}
