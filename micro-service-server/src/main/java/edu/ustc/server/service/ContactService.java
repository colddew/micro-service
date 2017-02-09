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


//@Query("MATCH (providerType:ProviderType {typeCode:{0}, description :{1} }) RETURN providerType")
//ProviderType findByTypeCodeAndDescription(String typeCode, String description);
//
//@Query("MATCH (suburb:Suburb {postcode:{0}, name :{1} }) RETURN suburb")
//Suburb findSuburbByPostcodeAndSuburb(Long postcode, String name);
//
//@Query("MATCH (speciality:Speciality {specialtyCode:{0}, description :{1} }) RETURN speciality")
//Speciality findBySpecialityCodeAndDescription(String specialityCode, String description);
//
//
//
//public interface TrackRepository extends GraphRepository<Track>,SpatialRepository<Track> {
//@Query("match (t:Topic)<--(s:Session)<--(tr:Track) where t.name = {0} and tr.tst = {1} return tr")
//public Track findByTopicAndTimestamp(String topic, Long timestamp);
//@Query("match (t:Topic)<--(s:Session)<--(tr:Track) where id(t)={0} return tr")
//public Result<Track> findAllForTopic(Long topicId);
//@Query("match (s:Session)<--(tr:Track) where id(s)={0} return tr order by tr.tst")
//public Result<Track> findAllForSession(Long sessionId);
//@Query("start n=node:" + Track.wktIndexName + "({0}) match (s:Session)<--(n) where id(s)={1} return s")
//public Result<Track> findWithinDistanceForSession(String withinDistance, Long sessionId);
//}
//
//
//public interface SessionRepository extends GraphRepository<Session> {
//@Query("match (t:Topic)<--(s:Session) where id(t)={0} return s order by s.date desc limit 1")
//public Session findLatestSession(Long topicId);
//@Query("match (t:Topic)<--(s:Session) where t.name={0} return s order by s.date desc limit 1")
//public Session findLatestSession(String topicName);
//@Query("match (t:Topic)<--(s:Session) where id(t)={0} return s order by s.date desc")
//public Result<Session> findSessionsByTopicId(Long topicId);
//}