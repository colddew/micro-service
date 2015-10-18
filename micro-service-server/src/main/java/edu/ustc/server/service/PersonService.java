package edu.ustc.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ustc.server.mapper.PersonMapper;
import edu.ustc.server.pojo.Person;

@Service
@Transactional
public class PersonService {
	
	@Autowired
	private PersonMapper personMapper;
	
	public List<Person> list() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		return personMapper.list(params);
	}
	
	public Person get(Integer pid) {
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
