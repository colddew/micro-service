package edu.ustc.server.config;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {
	
	public static final String DELIMITER_COLON = ":";
	
	@Autowired
	private RedisProperties redisProperties;
	
	@Bean
	public JedisPool getJedisPool() {
		
		if (hasMoreThanOneAddress()) {
			return null;
		}
		
		String[] hostAndPort = getHostAndPort();
		
		return new JedisPool(getPoolConfig(), hostAndPort[0], Integer.parseInt(hostAndPort[1]), getTimeout());
	}
	
	private boolean hasMoreThanOneAddress() {
		return redisProperties.getAddressList().size() > 1;
	}
	
	private String[] getHostAndPort() {
		return redisProperties.getAddressList().get(0).split(DELIMITER_COLON);
	}
	
	private int getTimeout() {
		return redisProperties.getTimeout() > 0 ? redisProperties.getTimeout() : Protocol.DEFAULT_TIMEOUT;
	}
	
	private JedisPoolConfig getPoolConfig() {
		
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMinIdle(redisProperties.getMinIdle());
        config.setMaxTotal(redisProperties.getMaxTotal());
        config.setMaxIdle(redisProperties.getMaxIdle());
        config.setMaxWaitMillis(redisProperties.getMaxWaitMillis());
        
		return config;
	}
	
	@Bean
	public JedisCluster getJedisCluster() {
		
		if (hasOlnyOneAddress()) {
			return null;
		}
		
		return new JedisCluster(getClusterNodes(), getTimeout(), getClusterConfig());
	}
	
	private boolean hasOlnyOneAddress() {
		return redisProperties.getAddressList().size() == 1;
	}
	
	private Set<HostAndPort> getClusterNodes() {
		
		Set<HostAndPort> nodes = new HashSet<>();
        for (String address : redisProperties.getAddressList()) {
            String[] hostAndPort = address.split(DELIMITER_COLON);
            nodes.add(new HostAndPort(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
        }
        
		return nodes;
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
