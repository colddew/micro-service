package edu.ustc.server.service;

import edu.ustc.server.mongo.company.Company;
import edu.ustc.server.mongo.company.Company.Status;
import edu.ustc.server.mongo.company.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	@Autowired
	private CompanyRepository companyRepository;
	
	public String add(Company companyDto) {
		
		Company company = new Company();
		company.setName(companyDto.getName());
		company.setAddress(companyDto.getAddress());
		company.setHeadcount(companyDto.getHeadcount());
		company.setStatus(Status.OPEN);
		
		Company result = companyRepository.save(company);
		
		return result.getId();
	}
	
	public void delete(String id) {
		companyRepository.deleteById(id);
	}
	
	public void update(String id, Company companyDto) {
		
		Company company = findById(id);
		company.setName(companyDto.getName());
		company.setAddress(companyDto.getAddress());
		company.setHeadcount(companyDto.getHeadcount());
		
		companyRepository.save(company);
	}
	
	public List<Company> list() {
		
		testCustomMethods();
		
		return companyRepository.findAll();
	}

	private void testCustomMethods() {
		
		System.out.println("Count: " + companyRepository.count());
		
		Pageable pageable = PageRequest.of(0, 2);
		Page<Company> page = companyRepository.findAll(pageable);
		logger.info("Page: " + page);
		logger.info("Number: " + page.getNumber());
		logger.info("NumberOfElements: " + page.getNumberOfElements());
		logger.info("Size: " + page.getSize());
		logger.info("TotalElements: " + page.getTotalElements());
		logger.info("TotalPages: " + page.getTotalPages());
		logger.info("Content: " + page.getContent());
		
		logger.info("Headcount count: " + companyRepository.countByHeadcount(5));
		logger.info("Headcount list: " + companyRepository.findByHeadcount(5));
		
		page = companyRepository.findByHeadcountAndStatus(3, Status.OPEN, pageable);
		logger.info("Page: " + page);
	}
	
	public Company findById(String id) {
		return companyRepository.findById(id).orElse(null);
	}
	
	public Company findByName(String name) {
		return companyRepository.findByName(name);
	}
	
	public List<Company> findByAddress(String address) {
		return companyRepository.findByAddress(address);
	}
}
