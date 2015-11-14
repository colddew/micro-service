package edu.ustc.server.mongo.company;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {
	
	public Company findByName(String name);
	
	public List<Company> findByAddress(String address);
}
