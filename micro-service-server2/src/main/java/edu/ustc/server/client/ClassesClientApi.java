package edu.ustc.server.client;

import java.util.List;

import edu.ustc.server.pojo.Classes;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface ClassesClientApi {
	
	@GET("/classes")
	public List<Classes> list();
	
	@PUT("/classes/{id}")
	public Callback<Void> update(@Path("id") Integer id, @Body Classes classesDto);
}
