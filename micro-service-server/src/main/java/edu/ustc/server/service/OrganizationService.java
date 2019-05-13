package edu.ustc.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ustc.server.mongo.organization.Organization;
import edu.ustc.server.mongo.organization.OrganizationRepository;

@Service
public class OrganizationService {
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	public String add(Organization organizationDto) {
		
		Organization organization = new Organization();
		organization.setName(organizationDto.getName());
		
		Organization result = organizationRepository.save(organization);
		
		return result.getId();
	}
	
	public void delete(String id) {
		organizationRepository.deleteById(id);
	}
	
	public List<Organization> list() {
		return organizationRepository.findAll();
	}
}
