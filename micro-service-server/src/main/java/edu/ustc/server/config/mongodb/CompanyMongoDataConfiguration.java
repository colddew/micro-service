package edu.ustc.server.config.mongodb;

import java.net.UnknownHostException;

import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages="edu.ustc.server.mongo.company")
public class CompanyMongoDataConfiguration extends MongoDataAutoConfiguration {
	
	public CompanyMongoDataConfiguration(ApplicationContext applicationContext, MongoProperties properties) {
		super(applicationContext, properties);
	}
	
	@Override
	@Bean
	@Primary
	public SimpleMongoDbFactory mongoDbFactory(MongoClient mongo) throws Exception {
		return super.mongoDbFactory(mongo);
	}
	
	@Override
	@Bean
	@Primary
	public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MongoConverter converter)
			throws UnknownHostException {
		return super.mongoTemplate(mongoDbFactory, converter);
	}
}
