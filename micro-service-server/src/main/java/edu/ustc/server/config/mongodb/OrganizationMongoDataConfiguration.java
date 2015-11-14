package edu.ustc.server.config.mongodb;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@EnableConfigurationProperties(OrganizationMongoProperties.class)
@AutoConfigureAfter(OrganizationMongoConfiguration.class)
@EnableMongoRepositories(basePackages="edu.ustc.server.mongo.organization", mongoTemplateRef="organizationMongoTemplate")
public class OrganizationMongoDataConfiguration {
	
	@Autowired
	private OrganizationMongoProperties properties;

	@Bean
	public MongoDbFactory organizationMongoDbFactory(@Qualifier("organizationMongo") MongoClient mongo) throws Exception {
		String database = this.properties.getMongoClientDatabase();
		return new SimpleMongoDbFactory(mongo, database);
	}

	@Bean
	public MongoTemplate organizationMongoTemplate(@Qualifier("organizationMongoDbFactory") MongoDbFactory mongoDbFactory,
			MongoConverter converter) throws UnknownHostException {
		return new MongoTemplate(mongoDbFactory, converter);
	}
}
