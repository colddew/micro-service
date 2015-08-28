package edu.ustc.server.mapper;

import java.util.List;
import java.util.Map;

import edu.ustc.server.pojo.Classes;

public interface ClassesMapper {
	
	int insert(Classes classes);
	
	Classes selectById(Integer id);
	
	List<Classes> list(Map<String, Object> params);
	
	int update(Classes classes);
	
    int delete(Integer id);
}