package edu.ustc.server.mongo.company;

import edu.ustc.server.mongo.company.Company.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CompanyRepository extends MongoRepository<Company, String> {
	
	Company findByName(String name);
	
	List<Company> findByAddress(String address);
	
	@Query("{'headcount':{'$gte':?0}}")
	List<Company> findByHeadcount(int headcount);
	
	@Query(value = "{'headcount':{'$gte':?0}}", count = true)
	int countByHeadcount(int headcount);
	
	Page<Company> findByHeadcountAndStatus(int headcount, Status status, Pageable pageable);
}
