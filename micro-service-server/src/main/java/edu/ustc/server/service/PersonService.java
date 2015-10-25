package edu.ustc.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ustc.server.mapper.PersonMapper;
import edu.ustc.server.pojo.Person;

@Service
@Transactional
public class PersonService {
	
	@Autowired
	private PersonMapper personMapper;
	
	@Value("${person.register.pre.day:60000}")
	private String preDayRegisterQuantity;
	
	@Value("${person.register.total:2000000000}")
	private String totalRegisterQuantity;
	
	public List<Person> list() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		return personMapper.list(params);
	}
	
	public Person get(Integer pid) {
		
		System.out.println("person.register.pre.day:" + preDayRegisterQuantity);
		System.out.println("person.register.total:" + totalRegisterQuantity);
		
		return personMapper.selectById(pid);
	}
	
	public void add(Person person) {
		personMapper.insert(person);
	}
	
	public void update(Integer pid, Person personDto) {
		
		Person person = get(pid);
		person.setName(personDto.getName());
		person.setAge(personDto.getAge());
		
		personMapper.update(person);
	}
	
	public void delete(Integer pid) {
		personMapper.delete(pid);
	}
}
