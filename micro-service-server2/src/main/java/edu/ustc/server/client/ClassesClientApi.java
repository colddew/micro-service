package edu.ustc.server.client;

import java.util.List;

import edu.ustc.server.pojo.Classes;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface ClassesClientApi {
	
	@GET("/api/v1/classes")
	public List<Classes> list();
	
	@PUT("/api/v1/classes/{id}")
	public Void update(@Path("id") Integer id, @Body Classes classesDto);
}
