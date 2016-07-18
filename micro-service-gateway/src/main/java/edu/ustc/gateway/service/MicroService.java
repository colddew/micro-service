package edu.ustc.gateway.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.ustc.gateway.service.interceptor.SessionRequestInterceptor;
import edu.ustc.server.client.ClassesClientApi;
import edu.ustc.server.client.PersonClientApi;
import edu.ustc.server.pojo.Classes;
import edu.ustc.server.pojo.Person;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Service
public class MicroService {
	
	private static PersonClientApi personClientApi;
	private static ClassesClientApi classesClientApi;
	
	static {
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create();
		
		RestAdapter personRestAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint("http://localhost:9001")
			.setConverter(new GsonConverter(gson)).setRequestInterceptor(new SessionRequestInterceptor()).setClient(new MicroServiceClient()).build();
		personClientApi = personRestAdapter.create(PersonClientApi.class);
		
		RestAdapter classesRestAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint("http://localhost:9002")
			.setConverter(new GsonConverter(gson)).setRequestInterceptor(new SessionRequestInterceptor()).setClient(new MicroServiceClient()).build();
		classesClientApi = classesRestAdapter.create(ClassesClientApi.class);
	}
	
	public static void list() {
		
		Gson gson = new Gson();
		
		List<Person> personList = personClientApi.list();
		System.out.println(personList);
		System.out.println(gson.toJson(personList));
		
		System.out.println("########################");
		
		List<Classes> classesList = classesClientApi.list();
		System.out.println(classesList);
		System.out.println(gson.toJson(classesList));
	}
	
	public static void update() {
		
		Person personDto = new Person();
		personDto.setId(6);
		personDto.setPid(6);
		personDto.setName("test6");
		personDto.setAge(60);
		personClientApi.update(personDto.getPid(), personDto);
		
		System.out.println("########################");
		
		Classes classesDto = new Classes();
		classesDto.setId(6);
		classesDto.setGrade("年级xxx");
		classesClientApi.update(classesDto.getId(), classesDto);
	}
	
	public static void main(String[] args) {
		MicroService.list();
//		MicroService.update();
	}
}
