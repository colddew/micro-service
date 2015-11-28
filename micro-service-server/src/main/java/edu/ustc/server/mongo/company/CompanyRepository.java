package edu.ustc.server.mongo.company;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import edu.ustc.server.mongo.company.Company.Status;

public interface CompanyRepository extends MongoRepository<Company, String> {
	
	public Company findByName(String name);
	
	public List<Company> findByAddress(String address);
	
	@Query("{'headcount':{'$gte':?0}}")
	public List<Company> findByHeadcount(int headcount);
	
	@Query(value = "{'headcount':{'$gte':?0}}", count = true)
	public int countByHeadcount(int headcount);
	
	public Page<Company> findByHeadcountAndStatus(int headcount, Status status, Pageable pageable);
}
