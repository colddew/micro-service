package edu.ustc.server.client;

import edu.ustc.server.pojo.Classes;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface ClassesClientApi {
	
	@GET("/api/v1/classes")
	List<Classes> list();
	
	@PUT("/api/v1/classes/{id}")
	Void update(@Path("id") Integer id, @Body Classes classesDto);
}
