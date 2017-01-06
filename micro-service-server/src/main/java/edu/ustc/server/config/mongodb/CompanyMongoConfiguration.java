package edu.ustc.server.config.mongodb;

import java.net.UnknownHostException;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;

@Configuration
public class CompanyMongoConfiguration extends MongoAutoConfiguration {

	public CompanyMongoConfiguration(MongoProperties properties, ObjectProvider<MongoClientOptions> optionsProvider,
			Environment environment) {
		super(properties, optionsProvider, environment);
	}

	@Override
	@Bean
	@Primary
	public MongoClient mongo() throws UnknownHostException {
		return super.mongo();
	}
}
