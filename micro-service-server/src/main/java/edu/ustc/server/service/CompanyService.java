package edu.ustc.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ustc.server.mongo.Company;
import edu.ustc.server.mongo.CompanyRepository;

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
		
		companyRepository.save(company);
	}
	
	public List<Company> list() {
		return companyRepository.findAll();
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
