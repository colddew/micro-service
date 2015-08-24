package edu.ustc.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.ustc.server.pojo.Person;

@RestController
@EnableAutoConfiguration
public class PersonController {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	
    @RequestMapping("/")
    public String hello() {
    	
    	logger.info("invoke hello method...");
    	
        return "Hello World!";
    }
    
    @RequestMapping("/person")
    public Person getPerson() {
    	
    	logger.info("invoke getPerson method...");
    	
    	Person person = new Person();
    	person.setId(1);
    	person.setName("dhc");
    	person.setAge(33);
    	
    	return person;
    }
    
	@RequestMapping(value = "/get", method = { RequestMethod.GET })
    public String get(String p) {
		
		logger.info("invoke get method...");
		
		return p;
    }
    
	@RequestMapping(value = "/post", method = { RequestMethod.POST })
    public String post(String p) {
		
		logger.info("invoke post method...");
		
		return p;
    }
    
    public static void main(String[] args) throws Exception {
    	
    	logger.info("micro-web project is starting...");
    	
        SpringApplication.run(PersonController.class, args);
    	
        logger.info("micro-web project has started...");
    }
}