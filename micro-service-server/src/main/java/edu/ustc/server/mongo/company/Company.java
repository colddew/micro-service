package edu.ustc.server.mongo.company;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;

public class Company {
	
	@Id
	private String id;
	
	private String name;
	private String address;
	private int headcount;
	
	public Company() {
		
	}
	
	public Company(String id, String name, String address, int headcount) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.headcount = headcount;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getHeadcount() {
		return headcount;
	}

	public void setHeadcount(int headcount) {
		this.headcount = headcount;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
