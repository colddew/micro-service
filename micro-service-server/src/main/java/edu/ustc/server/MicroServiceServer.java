package edu.ustc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.ustc.server.listener.EnvironmentPreparedEventApplicationListener;
import edu.ustc.server.listener.FailedEventApplicationListener;
import edu.ustc.server.listener.PreparedEventApplicationListener;
import edu.ustc.server.listener.StartedEventApplicationListener;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class MicroServiceServer {
	
	private static final Logger logger = LoggerFactory.getLogger(MicroServiceServer.class);
	
	public static void main(String[] args) throws Exception {
    	
		SpringApplication application = new SpringApplication(MicroServiceServer.class);
		application.addListeners(new StartedEventApplicationListener(), 
				new EnvironmentPreparedEventApplicationListener(), 
				new PreparedEventApplicationListener(), 
				new FailedEventApplicationListener());
		application.run(args);
    	
        logger.info("micro-service-server is running...");
    }
}
