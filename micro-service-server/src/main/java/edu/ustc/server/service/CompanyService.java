package edu.ustc.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.ustc.server.mongo.company.Company;
import edu.ustc.server.mongo.company.CompanyRepository;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	public String add(Company companyDto) {
		
		Company company = new Company();
		company.setName(companyDto.getName());
		company.setAddress(companyDto.getAddress());
		
		Company result = companyRepository.save(company);
		
		return result.getId();
	}
	
	public void delete(String id) {
		companyRepository.delete(id);
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
		
		Pageable pageable = new PageRequest(2, 2);
		Page<Company> page = companyRepository.findAll(pageable);
		System.out.println("Page: " + page);
		System.out.println("Number: " + page.getNumber());
		System.out.println("NumberOfElements: " + page.getNumberOfElements());
		System.out.println("Size: " + page.getSize());
		System.out.println("TotalElements: " + page.getTotalElements());
		System.out.println("TotalPages: " + page.getTotalPages());
		System.out.println("Content: " + page.getContent());
		
		System.out.println("Headcount count: " + companyRepository.countByHeadcount(5));
		System.out.println("Headcount list: " + companyRepository.findByHeadcount(5));
	}
	
	public Company findById(String id) {
		return companyRepository.findOne(id);
	}
	
	public Company findByName(String name) {
		return companyRepository.findByName(name);
	}
	
	public List<Company> findByAddress(String address) {
		return companyRepository.findByAddress(address);
	}
}
