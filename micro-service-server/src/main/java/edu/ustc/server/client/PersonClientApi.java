package edu.ustc.server.client;

import java.util.List;

import edu.ustc.server.pojo.Person;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface PersonClientApi {
	
	@GET("/person")
	public List<Person> list();
	
	@PUT("/person/{id}")
	public Callback<Void> update(@Path("id") Integer id, @Body Person personDto);
}
