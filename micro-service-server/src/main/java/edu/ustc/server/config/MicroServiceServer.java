package edu.ustc.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "edu.ustc.server" })
public class MicroServiceServer {
	
	private static final Logger logger = LoggerFactory.getLogger(MicroServiceServer.class);
	
	public static void main(String[] args) throws Exception {
    	
    	SpringApplication.run(MicroServiceServer.class, args);
    	
        logger.info("micro-service-server is running...");
    }
}
