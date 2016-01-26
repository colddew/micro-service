package edu.ustc.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import edu.ustc.server.annotation.MicroServiceCondition;

@Configuration
public class ConditionConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(ConditionConfig.class);
	
	@Conditional(MicroServiceCondition.class)
	@Bean(name = "defaultConfigBean")
	public Object defaultConfigBean() {
		
		logger.info("create new DefaultConfigBean...");
		
		return new Object();
	}
	
	@Conditional(MicroServiceCondition.class)
	@Bean(name = "conditionConfigBean")
	public Object conditionConfigBean() {
		
		logger.info("create new ConditionConfigBean...");
		
		return new Object();
	}
}
