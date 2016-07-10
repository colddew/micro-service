package edu.ustc.server.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;

public class RedisUtils {
	
	public static JedisCommands getJedisCommands(JedisCluster jedisCluster, JedisPool jedisPool) {
		
		if(null != jedisCluster) {
			return jedisCluster;
		} else if(null != jedisPool) {
			return jedisPool.getResource();
		}
		
		return null;
	}
	
	public static void releaseJedisConnection(JedisCommands jedisCommands) {
		if(null != jedisCommands && jedisCommands instanceof Jedis) {
			((Jedis) jedisCommands).close();
		}
	}
}