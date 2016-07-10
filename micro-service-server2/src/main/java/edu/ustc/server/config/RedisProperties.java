package edu.ustc.server.config;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "microservice.redis")
@ConditionalOnProperty("microservice.redis.addressList")
public class RedisProperties {
	
	private int minIdle;
    private int maxIdle;
    private int maxTotal;
    private long maxWaitMillis;
    private int timeout;
    private List<String> addressList;
    
	public int getMinIdle() {
		return minIdle;
	}
	
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}
	
	public int getMaxIdle() {
		return maxIdle;
	}
	
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	
	public int getMaxTotal() {
		return maxTotal;
	}
	
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}
	
	public long getMaxWaitMillis() {
		return maxWaitMillis;
	}
	
	public void setMaxWaitMillis(long maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}
	
	public int getTimeout() {
		return timeout;
	}
	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	public List<String> getAddressList() {
		return addressList;
	}
	
	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
	}
}