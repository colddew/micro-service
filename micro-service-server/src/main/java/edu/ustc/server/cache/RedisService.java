package edu.ustc.server.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisCluster;

@Service
@EnableScheduling
public class RedisService {
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Scheduled(fixedDelay = 1000 * 60 * 60)
	public void print() {
		System.out.println(jedisCluster);
	}
}
