package edu.ustc.server.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Protocol;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class RedisClusterConfig {
	
	public static final String DELIMITER_COLON = ":";

	@Autowired
	private RedisProperties redisProperties;
	
	@Bean
	public JedisCluster jedisCluster() {
		
        if(CollectionUtils.isEmpty(redisProperties.getNodes())) {
			throw new RuntimeException("load redis properties error");
		}
        
		return new JedisCluster(getClusterNodes(), getTimeout(), getClusterConfig());
	}

	private Set<HostAndPort> getClusterNodes() {

		Set<HostAndPort> nodes = new HashSet<>();
		for (String node : redisProperties.getNodes()) {

			String[] hostAndPort = node.split(DELIMITER_COLON);
			if(null != hostAndPort && hostAndPort.length == 2) {
				nodes.add(new HostAndPort(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
			}
		}

		return nodes;
	}

	private int getTimeout() {
		return redisProperties.getTimeout() > 0 ? redisProperties.getTimeout() : Protocol.DEFAULT_TIMEOUT;
	}

	private GenericObjectPoolConfig getClusterConfig() {

		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setTestWhileIdle(true);
		config.setMinIdle(redisProperties.getMinIdle());
		config.setMaxIdle(redisProperties.getMaxIdle());
		config.setMaxTotal(redisProperties.getMaxTotal());
		config.setMaxWaitMillis(redisProperties.getMaxWaitMillis());

		return config;
	}
}
