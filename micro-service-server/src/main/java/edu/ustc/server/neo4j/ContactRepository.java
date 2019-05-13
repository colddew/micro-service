package edu.ustc.server.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface ContactRepository extends Neo4jRepository<Contact, Long> {

	List<Contact> findByUserId(Long userId);
}
