package edu.ustc.gateway.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;

public class MicroServiceClient implements Client {
	
	private static final Logger logger = LoggerFactory.getLogger(MicroServiceClient.class);
	
	@Override
	public Response execute(Request request) throws IOException {
		
		logger.info("invoke remote microservice by self-define retrofit client, the url is {}" , request.getUrl());
		
		OkClient client = new OkClient();
		
		return client.execute(request);
	}
}
