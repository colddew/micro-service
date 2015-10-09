package edu.ustc.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ustc.server.mapper.ClassesMapper;
import edu.ustc.server.pojo.Classes;

@Service
@Transactional
public class ClassesService {
	
	@Autowired
	private ClassesMapper classesMapper;
	
	public List<Classes> list() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		return classesMapper.list(params);
	}
	
	public Classes get(Integer id) {
		return classesMapper.selectById(id);
	}
	
	public void add(Classes classes) {
		classesMapper.insert(classes);
	}
	
	public void update(Integer id, Classes classesDto) {
		
		Classes classes = get(id);
		classes.setGrade(classesDto.getGrade());
		
		classesMapper.update(classes);
	}
	
	public void delete(Integer id) {
		classesMapper.delete(id);
	}
}
