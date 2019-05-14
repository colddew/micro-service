package edu.ustc.server.health;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

@Component
public class CassandraHealthIndicator extends AbstractHealthIndicator {
	
	private static final Logger logger = LoggerFactory.getLogger(CassandraHealthIndicator.class);
	
	@Autowired
	private Cluster cluster;
	
	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		
		Session session = null;
		
		try {
			session = cluster.connect();
			ResultSet rs = session.execute("SELECT dateof(now()) FROM system.local");
			Date date = rs.one().getTimestamp(0);
			logger.info("cassandra is working, the time is ", date);
			builder.up().withDetail("description", "cassandra is working, the time is " + date);
		} catch (Exception e) {
			logger.error("cassandra launch has error, {}", e.getMessage());
			builder.outOfService().withDetail("description", "cassandra launch has error, " + e.getMessage());
		} finally {
			if(null != session) {
				session.close();
			}
		}
	}
}
