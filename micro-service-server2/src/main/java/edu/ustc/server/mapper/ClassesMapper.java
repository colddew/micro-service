package edu.ustc.server.mapper;

import edu.ustc.server.pojo.Classes;

public interface ClassesMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Classes record);

    int insertSelective(Classes record);

    Classes selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Classes record);

    int updateByPrimaryKey(Classes record);
}