package edu.ustc.server.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.google.code.shardbatis.plugin.ShardPlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "edu.ustc.server.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DatasourceConfig {

    // multi data source
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource dataSourceOne() {
        return DruidDataSourceBuilder.create().build();
    }

//    @Bean
//    @ConfigurationProperties("spring.datasource.druid.two")
//    public DataSource dataSourceTwo() {
//        return DruidDataSourceBuilder.create().build();
//    }

    @Bean
    public DataSourceTransactionManager transactionManagerOne(@Qualifier("dataSourceOne") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

//    @Bean
//    public DataSourceTransactionManager transactionManagerTwo(@Qualifier("dataSourceTwo") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }

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
