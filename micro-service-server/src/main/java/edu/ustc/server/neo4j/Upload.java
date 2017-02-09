package edu.ustc.server.neo4j;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "UPLOAD")
public class Upload {

	@GraphId
	private Long relationshipId;
	@Property
	private String alias;
	@StartNode
	private Contact from;
	@EndNode
	private Contact to;

	public Long getRelationshipId() {
		return relationshipId;
	}

	public void setRelationshipId(Long relationshipId) {
		this.relationshipId = relationshipId;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Contact getFrom() {
		return from;
	}

	public void setFrom(Contact from) {
		this.from = from;
	}

	public Contact getTo() {
		return to;
	}

	public void setTo(Contact to) {
		this.to = to;
	}
}
