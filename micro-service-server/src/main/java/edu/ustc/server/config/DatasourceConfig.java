package edu.ustc.server.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.code.shardbatis.plugin.ShardPlugin;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "edu.ustc.server.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DatasourceConfig {
	
    @Autowired
    private Environment env;
    
    @Bean
    public DataSource dataSource() {
    	
        Properties dbProperties = new Properties();
        Map<String, Object> map = new HashMap<String, Object>();
        for (Iterator<PropertySource<?>> it = ((AbstractEnvironment) env).getPropertySources().iterator(); it.hasNext();) {
            PropertySource<?> propertySource = it.next();
            getPropertiesFromSource(propertySource, map);
        }
        dbProperties.putAll(map);
        
        DruidDataSource dataSource = null;
        try {
        	dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(dbProperties);
        	if(null != dataSource) {
        		dataSource.setFilters("wall,stat");
//        		dataSource.setTimeBetweenLogStatsMillis(5000);
        		dataSource.init();
        	}
        } catch (Exception e) {
        	throw new RuntimeException("load datasource error, dbProperties is :" + dbProperties, e);
        }
        
        return dataSource;
    }
    
    private void getPropertiesFromSource(PropertySource<?> propertySource, Map<String, Object> map) {
    	
        if (propertySource instanceof MapPropertySource) {
            for (String key : ((MapPropertySource) propertySource).getPropertyNames()) {
                map.put(key, propertySource.getProperty(key));
            }
        }
        
        if (propertySource instanceof CompositePropertySource) {
            for (PropertySource<?> s : ((CompositePropertySource) propertySource).getPropertySources()) {
                getPropertiesFromSource(s, map);
            }
        }
    }
    
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
    
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
        
    	try {
            final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource);
            sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*Mapper.xml"));
            sessionFactory.setPlugins(new Interceptor[] { getShardPlugin() });
            
            return sessionFactory.getObject();
        } catch (Exception e) {
        	throw new RuntimeException("sqlSessionFactory configuration error", e);
        }
    }
    
    private Interceptor getShardPlugin() {
    	
    	Properties properties = new Properties();
    	properties.setProperty("shardingConfig", "shardConfig.xml");
    	
    	ShardPlugin shardPlugin = new ShardPlugin();
    	shardPlugin.setProperties(properties);
    	
    	return shardPlugin;
    }
}
