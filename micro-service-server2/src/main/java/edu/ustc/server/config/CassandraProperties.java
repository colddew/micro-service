package edu.ustc.server.config;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "microservice.cassandra")
@ConditionalOnProperty("microservice.cassandra.contact-points")
public class CassandraProperties {
	
	private String username;
	private String password;
	private List<String> contactPoints;
	private int port;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<String> getContactPoints() {
		return contactPoints;
	}
	
	public void setContactPoints(List<String> contactPoints) {
		this.contactPoints = contactPoints;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
}