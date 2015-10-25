package edu.ustc.server.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

@Configuration
public class RedisClusterConfig {
	
	public static final String DELIMITER_SEMICOLON = ";";
	public static final String DELIMITER_COLON = ":";
	
	@Value("${redis.cluster.nodes}")
	private String clusterNodes;
	
	@Value("${redis.timeout}")
	private int timeout;
	
	@Bean
	public JedisCluster jedisCluster() {
		
        if(StringUtils.isEmpty(clusterNodes)) {
			throw new RuntimeException("load redis properties error");
		}
        
		return new JedisCluster(configRedisCluster(clusterNodes), timeout);
	}
	
	private static Set<HostAndPort> configRedisCluster(String clusterNodes) {
		
		Set<HostAndPort> redisCluster = new HashSet<HostAndPort>();
		
		String[] nodes = clusterNodes.split(DELIMITER_SEMICOLON);
		if(null != nodes && nodes.length > 0) {
			for(int i=0; i<nodes.length; i++) {
				String[] hostAndPort = nodes[i].split(DELIMITER_COLON);
				if(null != hostAndPort && hostAndPort.length == 2) {
					redisCluster.add(new HostAndPort(hostAndPort[0], Integer.valueOf(hostAndPort[1])));
				}
			}
		}
		
		return redisCluster;
	}
}
