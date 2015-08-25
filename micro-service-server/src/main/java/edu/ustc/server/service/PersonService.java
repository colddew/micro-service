package edu.ustc.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import edu.ustc.server.mapper.PersonMapper;
import edu.ustc.server.pojo.Person;

@Service
public class PersonService {
	
	@Autowired
	private PersonMapper personMapper;
	
	public List<Person> list() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		return personMapper.list(params);
	}
	
	public Person get(Integer id) {
		return personMapper.selectById(id);
	}
	
	public void add(Person person) {
		personMapper.insert(person);
	}
	
	public void update(Integer id, Person personDto) {
		
		Person person = get(id);
		person.setName(personDto.getName());
		person.setAge(personDto.getAge());
		
		personMapper.update(person);
	}
	
	public void delete(Integer id) {
		personMapper.delete(id);
	}
}
