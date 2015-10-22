package edu.ustc.server.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

@Configuration
public class RedisClusterConfig {
	
	public static final String DELIMITER_SEMICOLON = ";";
	public static final String DELIMITER_COLON = ":";
	public static final String CLUSTER_NODES = "127.0.0.1:7000;127.0.0.1:7001;127.0.0.1:7002;127.0.0.1:7003;127.0.0.1:7004;127.0.0.1:7005";
	public static final int TIMEOUT = 5000;
	
	@Bean
	public JedisCluster jedisCluster() {
		return new JedisCluster(configRedisCluster(CLUSTER_NODES), TIMEOUT);
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
