package edu.ustc.server.config.mongodb;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MultiMongoProperties {

    @Primary
    @Bean(name="companyMongoProperties")
    @ConfigurationProperties(prefix="spring.data.mongodb.company")
    public MongoProperties companyMongoProperties() {
        return new MongoProperties();
    }

    @Bean(name="organizationMongoProperties")
    @ConfigurationProperties(prefix="spring.data.mongodb.organization")
    public MongoProperties organizationMongoProperties() {
        return new MongoProperties();
    }
}