package edu.ustc.server.cache;

import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisCluster;

@Service
public class RedisService {
	
	private static final JedisCluster redis = RedisCluster.getJedisCluster();
	
	public static void main(String[] args) {
		System.out.println(redis);
		System.out.println(redis);
		System.out.println(redis);
	}
}
