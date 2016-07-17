package edu.ustc.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ustc.server.cassandra.Customer;
import edu.ustc.server.cassandra.CustomerDao;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	
	public List<Customer> list() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		return customerDao.list(params);
	}
	
	public Customer get(UUID customerId) {
		return customerDao.get(customerId);
	}
	
	public void save(Customer customer) {
		customerDao.save(customer);
	}
	
	public void delete(UUID id) {
		customerDao.delete(id);
	}
}
