package edu.ustc.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicPropertyFactory;

import edu.ustc.server.config.DynamicConfig;
import edu.ustc.server.mapper.PersonMapper;
import edu.ustc.server.pojo.Person;

@Service
@Transactional
public class PersonService {
	
	@Autowired
	private PersonMapper personMapper;
	
	@Autowired
	private DynamicConfig dynamicConfig;
	
	@Value("${person.register.pre.day:600}")
	private int preDayRegisterQuantity;
	
	@Value("${person.register.total:20000}")
	private int totalRegisterQuantity;
	
	public Integer getPreDayRegisterQuantity() {
		return dynamicConfig.getPreDayRegisterQuantity();
	}
	
	public Integer getTotalRegisterQuantity() {
		return dynamicConfig.getTotalRegisterQuantity();
	}
	
	public List<Person> list() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		return personMapper.list(params);
	}
	
	public Person get(Integer pid) {
		
		System.out.println("person.register.pre.day:fix:" + preDayRegisterQuantity);
		System.out.println("person.register.pre.day:dynamic:" + getPreDayRegisterQuantity());
		System.out.println("person.register.pre.day:netflix:" + ConfigurationManager.getConfigInstance().getInteger("person.register.pre.day", 600));
		System.out.println("person.register.pre.day:netflix:" + DynamicPropertyFactory.getInstance().getIntProperty("person.register.pre.day", 600).get());
		
		System.out.println("person.register.total:fix:" + totalRegisterQuantity);
		System.out.println("person.register.total:dynamic:" + getTotalRegisterQuantity());
		System.out.println("person.register.pre.day:netflix:" + ConfigurationManager.getConfigInstance().getInteger("person.register.total", 20000));
		System.out.println("person.register.pre.day:netflix:" + DynamicPropertyFactory.getInstance().getIntProperty("person.register.total", 20000).get());
		
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
