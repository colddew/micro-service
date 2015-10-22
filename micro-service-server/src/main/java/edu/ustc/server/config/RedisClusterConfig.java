package edu.ustc.server.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

@Configuration
public class RedisClusterConfig {
	
	public static final String CLUSTER_NODES = "redis.cluster.nodes";
	public static final String TIMEOUT = "redis.timeout";
	public static final String DELIMITER_SEMICOLON = ";";
	public static final String DELIMITER_COLON = ":";
	
	@Autowired
    private Environment env;
	
	@Bean
	public JedisCluster jedisCluster() {
		
		Map<String, Object> map = loadProperties();
        if(StringUtils.isEmpty(map.get(CLUSTER_NODES)) || StringUtils.isEmpty(map.get(TIMEOUT))) {
			throw new RuntimeException("load redis properties error");
		}
        
		return new JedisCluster(configRedisCluster((String) map.get(CLUSTER_NODES)), Integer.valueOf((String) map.get(TIMEOUT)));
	}
	
	private Map<String, Object> loadProperties() {
		
		Map<String, Object> map = new HashMap<String, Object>();
        for (Iterator<PropertySource<?>> it = ((AbstractEnvironment) env).getPropertySources().iterator(); it.hasNext();) {
            PropertySource<?> propertySource = it.next();
            getPropertiesFromSource(propertySource, map);
        }
        
        return map;
	}
	
	private void getPropertiesFromSource(PropertySource<?> propertySource, Map<String, Object> map) {
    	
        if (propertySource instanceof MapPropertySource) {
            for (String key : ((MapPropertySource) propertySource).getPropertyNames()) {
                map.put(key, propertySource.getProperty(key));
            }
        }
        
        if (propertySource instanceof CompositePropertySource) {
            for (PropertySource<?> s : ((CompositePropertySource) propertySource).getPropertySources()) {
                getPropertiesFromSource(s, map);
            }
        }
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
