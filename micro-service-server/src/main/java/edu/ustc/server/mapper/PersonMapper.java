package edu.ustc.server.mapper;

import java.util.List;
import java.util.Map;

import edu.ustc.server.pojo.Person;

public interface PersonMapper {
	
	int insert(Person person);
    
    Person selectById(Integer id);
    
    List<Person> list(Map<String, Object> params);
    
    int update(Person person);
    
    int delete(Integer id);
}