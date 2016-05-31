package edu.ustc.server.client;

import java.util.List;

import edu.ustc.server.pojo.Person;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface PersonClientApi {
	
	@GET("/api/v1/person")
	public List<Person> list();
	
	@PUT("/api/v1/person/{id}")
	public Void update(@Path("id") Integer id, @Body Person personDto);
}
