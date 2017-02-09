package edu.ustc.server.neo4j;

import java.util.List;

import org.springframework.data.neo4j.repository.GraphRepository;

public interface ContactRepository extends GraphRepository<Contact> {
	
	List<Contact> findByUserId(Long userId);
}
