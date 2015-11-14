package edu.ustc.server.config.mongodb;

import java.net.UnknownHostException;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;

@Configuration
@EnableConfigurationProperties(OrganizationMongoProperties.class)
public class OrganizationMongoConfiguration {
	
	@Autowired
	private OrganizationMongoProperties properties;

	@Autowired(required = false)
	private MongoClientOptions options;

	@Autowired
	private Environment environment;

	private MongoClient mongo;

	@PreDestroy
	public void close() {
		if (this.mongo != null) {
			this.mongo.close();
		}
	}

	@Bean
	public MongoClient organizationMongo() throws UnknownHostException {
		this.mongo = this.properties.createMongoClient(this.options, this.environment);
		return this.mongo;
	}
}
