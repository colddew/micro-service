package edu.ustc.server.cassandra;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "mykeyspace", name = "customers", writeConsistency = "LOCAL_QUORUM", readConsistency = "LOCAL_QUORUM")
public class Customer {
	
	@PartitionKey
	private UUID customerId;
	
	private String firstName;
	private String lastName;
	
	public Customer() {
		
	}
	
	public Customer(UUID customerId, String firstName, String lastName) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public UUID getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return String.format("Customer[customerId=%s, firstName='%s', lastName='%s']", customerId, firstName, lastName);
	}
}