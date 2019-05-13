package edu.ustc.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@Configuration
@EnableNeo4jRepositories(basePackages = "edu.ustc.server.neo4j")
public class Neo4jConfig {

}
