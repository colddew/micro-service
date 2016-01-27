package edu.ustc.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
	
	@Autowired
	private PersonMapper personMapper;
	
	@Autowired
	private DynamicConfig dynamicConfig;
	
	@Autowired
	private RedisService redisService;
	
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
		
		loadConfig();
		concurrentLock();
		
		return personMapper.selectById(pid);
	}
	
	private void loadConfig() {
		
		logger.info("person.register.pre.day:fix:" + preDayRegisterQuantity);
		logger.info("person.register.pre.day:dynamic:" + getPreDayRegisterQuantity());
		logger.info("person.register.pre.day:netflix:" + ConfigurationManager.getConfigInstance().getInteger("person.register.pre.day", 600));
		logger.info("person.register.pre.day:netflix:" + DynamicPropertyFactory.getInstance().getIntProperty("person.register.pre.day", 600).get());
		
		logger.info("person.register.total:fix:" + totalRegisterQuantity);
		logger.info("person.register.total:dynamic:" + getTotalRegisterQuantity());
		logger.info("person.register.pre.day:netflix:" + ConfigurationManager.getConfigInstance().getInteger("person.register.total", 20000));
		logger.info("person.register.pre.day:netflix:" + DynamicPropertyFactory.getInstance().getIntProperty("person.register.total", 20000).get());
		
		logger.info("concurrent.quantity" + ConfigurationManager.getConfigInstance().getInteger("concurrent.quantity", 10));
	}
	
	private void concurrentLock() {
		
		ExecutorService service = Executors.newFixedThreadPool(ConfigurationManager.getConfigInstance().getInteger("concurrent.quantity", 10));
		service.submit(new Runnable() {
			@Override
			public void run() {
				if(redisService.lockUpdateOperation("123456")) {
					logger.info("##### first time locked #####");
				} else {
					logger.info("##### already locked #####");
				}
			}
		});
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
