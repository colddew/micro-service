package edu.ustc.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.testng.annotations.Test;

//@ContextConfiguration(classes = { xxx.class })
@Configuration
@PropertySource("classpath:application.properties")
@Test
public class ContactServiceTest {
	
	@Autowired
	private ContactService contactService;
	
	@Test
	public void testAddContact() {
		contactService.addContact("13777777777", 1001L, "13999999999", 2001L, "leave");
	}
}
