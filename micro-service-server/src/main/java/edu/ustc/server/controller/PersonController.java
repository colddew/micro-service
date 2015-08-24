package edu.ustc.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.ustc.server.pojo.Person;

@RestController
@EnableAutoConfiguration
@RequestMapping("/person")
public class PersonController {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	
	private static List<Person> list = new ArrayList<Person>();
	
	static {
		Person person = new Person();
    	person.setId(1);
    	person.setAge(18);
    	person.setName("google");
    	
    	Person person2 = new Person();
    	person2.setId(2);
    	person2.setAge(20);
    	person2.setName("fackbook");
    	
    	list.add(person);
    	list.add(person2);
	}
	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Person> list() {
    	
    	logger.info("invoke list method...");
    	
        return list;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Person get(@PathVariable("id") Integer id) {
		
		logger.info("invoke get method...");
		
		return list.get(id - 1);
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void add(Person person) {
		
    	logger.info("invoke add method...");
    	
    	list.add(person);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") Integer id, String name, Integer age) {
    	
    	logger.info("invoke update method...");
    	
    	Person person = list.get(id - 1);
    	person.setName(name);
    	person.setAge(age);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
    	
    	logger.info("invoke delete method...");
    	
    	list.remove(id -1);
    }
    
    public static void main(String[] args) throws Exception {
    	
    	SpringApplication.run(PersonController.class, args);
    	
        logger.info("start micro-service-server...");
    }
}