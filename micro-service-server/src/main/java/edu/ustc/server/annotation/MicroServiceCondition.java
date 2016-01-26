package edu.ustc.server.annotation;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.CollectionUtils;

public class MicroServiceCondition implements Condition {
	
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		
		if (metadata.isAnnotated(Bean.class.getName())) {
			
			Map<String, Object> map = metadata.getAnnotationAttributes(Bean.class.getName());
			if(!CollectionUtils.isEmpty(map)) {
				
				String[] names = (String[]) map.get("name");
				if(ArrayUtils.isNotEmpty(names)) {
					
					for(String name : names) {
						
						if(StringUtils.isNotBlank(name) && name.endsWith("ConfigBean")) {
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
}
