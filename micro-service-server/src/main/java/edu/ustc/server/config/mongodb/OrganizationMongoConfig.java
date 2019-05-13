package edu.ustc.server.config.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages="edu.ustc.server.mongo.organization", mongoTemplateRef="organizationMongoTemplate")
public class OrganizationMongoConfig {

	@Autowired
	@Qualifier("organizationMongoProperties")
	private MongoProperties mongoProperties;

	@Bean(name = "organizationMongoTemplate")
	public MongoTemplate organizationMongoTemplate() throws Exception {
		return new MongoTemplate(organizationFactory());
	}

	@Bean
	public MongoDbFactory organizationFactory() throws Exception {
		ServerAddress serverAddress = new ServerAddress(mongoProperties.getUri());
		return new SimpleMongoDbFactory(new MongoClient(serverAddress), mongoProperties.getDatabase());
	}
}
