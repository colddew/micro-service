package edu.ustc.server.utils;

import com.google.code.shardbatis.strategy.ShardStrategy;

import edu.ustc.server.pojo.Person;

public class PersonShardStrategy implements ShardStrategy {
	
	private static final Integer DIVISOR = 3;
	
	@Override
	public String getTargetTableName(String baseTableName, Object params, String mapperId) {
		return baseTableName + getTableNameSuffix(params);
	}
	
	private String getTableNameSuffix(Object params) {
		
		try {
			Integer pid = null;
			if (params instanceof Integer) {
				pid = (Integer) params;
			} else if (params instanceof Person) {
				pid = ((Person) params).getPid();
			}
			
			int remainder = pid % DIVISOR;
			if (0 == remainder) {
				return "_0";
			} else if (1 == remainder) {
				return "_1";
			} else if (2 == remainder) {
				return "_2";
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
		throw new RuntimeException("get shard table name error");
	}
}
