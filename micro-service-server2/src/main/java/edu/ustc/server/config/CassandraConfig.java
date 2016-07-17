package edu.ustc.server.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;

@Configuration
@EnableConfigurationProperties(CassandraProperties.class)
public class CassandraConfig {
	
	@Autowired
	private CassandraProperties cassandraProperties;
	
	@Bean
    public Cluster cassandraCluster() {
		
		Builder builder = Cluster.builder();
        
		if(StringUtils.isNoneBlank(cassandraProperties.getUsername()) && StringUtils.isNotBlank(cassandraProperties.getPassword())) {
        	builder = builder.withCredentials(cassandraProperties.getUsername(), cassandraProperties.getPassword());
        }
		
		String[] contactPoints = cassandraProperties.getContactPoints().toArray(new String[cassandraProperties.getContactPoints().size()]);
		builder = builder.addContactPoints(contactPoints).withPort(cassandraProperties.getPort());
        
		return builder.build();
    }
}
