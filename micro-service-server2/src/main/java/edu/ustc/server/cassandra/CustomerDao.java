package edu.ustc.server.cassandra;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

@Repository
public class CustomerDao {
	
	@Autowired
	private Cluster cluster;
	
	private Session session;
	private Mapper<Customer> customerMapper;
	
	@PostConstruct
	public void init() {
		session = cluster.connect("mykeyspace");
		customerMapper = new MappingManager(session).mapper(Customer.class);
		customerMapper.setDefaultSaveOptions(Mapper.Option.saveNullFields(false));
	}
	
	@PreDestroy
	public void destory() {
		if(null != session) {
			session.close();
		}
	}
	
	public List<Customer> list(Map<String, Object> params) {
		
		List<Customer> list = new ArrayList<>();
		
		ResultSet rs = session.execute("SELECT * FROM customers");
		for (Row row : rs) {
			
			Customer customer = new Customer();
			customer.setCustomerId(row.getUUID("customerId"));
			customer.setFirstName(row.getString("firstName"));
			customer.setLastName(row.getString("lastName"));
			
			list.add(customer);
		}
		
		return list;
	}
	
	public Customer get(UUID customerId) {
		Customer customer = customerMapper.get(customerId);
		return customer;
	}
	
	public void save(Customer customer) {
		customerMapper.save(customer);
	}
	
	public void delete(UUID customerId) {
    	customerMapper.delete(customerId);
    }
}
