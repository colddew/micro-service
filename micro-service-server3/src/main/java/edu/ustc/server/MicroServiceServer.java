package edu.ustc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
//@ServletComponentScan(basePackages="edu.ustc.server.servlet")
public class MicroServiceServer {

	private static final Logger logger = LoggerFactory.getLogger(MicroServiceServer.class);

	public static void main(String[] args) throws Exception {

//    	SpringApplication.run(MicroServiceServer.class, args);

		new SpringApplicationBuilder(MicroServiceServer.class)
				.web(WebApplicationType.REACTIVE)
				.properties("hello=world")
				.run(args);

		logger.info("micro-service-server3 is running...");
	}
}
