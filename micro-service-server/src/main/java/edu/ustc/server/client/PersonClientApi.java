package edu.ustc.server.client;

import edu.ustc.server.pojo.Person;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface PersonClientApi {
	
	@GET("/api/v1/person")
	List<Person> list();
	
	@PUT("/api/v1/person/{id}")
	Void update(@Path("id") Integer id, @Body Person personDto);
}
