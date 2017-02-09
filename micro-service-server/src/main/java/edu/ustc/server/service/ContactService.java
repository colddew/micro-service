package edu.ustc.server.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ustc.server.neo4j.Contact;
import edu.ustc.server.neo4j.ContactRepository;
import edu.ustc.server.neo4j.Upload;

@Service
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;
	
	public Long addContact(String fromPhone, Long fromUserId, String toPhone, Long toUserId, String alias) {
		
		long now = new Date().getTime();
		
		Contact fromContact = new Contact();
		fromContact.setPhone(fromPhone);
		fromContact.setUserId(fromUserId);
		fromContact.setCreateTime(now);
		fromContact.setLastModifyTime(now);
		
		Contact toContact = new Contact();
		toContact.setPhone(toPhone);
		toContact.setUserId(toUserId);
		toContact.setCreateTime(now);
		toContact.setLastModifyTime(now);
		
		Upload upload = new Upload();
		upload.setAlias(alias);
		upload.setFrom(fromContact);
		upload.setTo(toContact);
		
		Contact result = contactRepository.save(fromContact);
		return result.getContactId();
	}
	
	// (:Contact)-[:Upload {alias:"kevin"}->(:Contact)
	public void uploadContact() {
		
	}
	
	public List<String> getForwardPhones(String phone) {
		return null;
	}
	
	public List<String> getReversePhones(String phone) {
		return null;
	}
	
	public List<String> getAlias(String phone) {
		return null;
	}
	
	public Contact getContact(Long userId) {
		return null;
	}
	
	public Object getContacts(Long userId) {
		return null;
	}
}
