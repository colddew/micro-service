package edu.ustc.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.ustc.server.pojo.Person;
import edu.ustc.server.service.PersonService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/person")
public class PersonController {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
	private PersonService personService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Person> list() {
    	
    	logger.info("invoke list method...");
    	
        return personService.list();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Person get(@PathVariable("id") Integer id) {
		
		logger.info("invoke get method...");
		
		return personService.get(id);
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void add(@RequestBody Person person) {
		
    	logger.info("invoke add method...");
    	
    	personService.add(person);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") Integer id, @RequestBody Person personDto) {
    	
    	logger.info("invoke update method...");
    	
    	personService.update(id, personDto);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
    	
    	logger.info("invoke delete method...");
    	
    	personService.delete(id);
    }
}