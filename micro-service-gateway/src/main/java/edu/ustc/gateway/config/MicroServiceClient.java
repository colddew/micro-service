package edu.ustc.gateway.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;

public class MicroServiceClient implements Client {
	
	private static final Logger logger = LoggerFactory.getLogger(MicroServiceClient.class);
	
	public static final Integer IS_ASYNC_CLIENT_YES = 1;
	public static final Integer IS_ASYNC_CLIENT_NO = 0;
	
	private Integer isAsync;
	
	public Integer getIsAsync() {
		return isAsync;
	}
	
	public void setIsAsync(Integer isAsync) {
		this.isAsync = isAsync;
	}
	
	public MicroServiceClient(Integer isAsync) {
		this.isAsync = isAsync;
	}
	
	@Override
	public Response execute(Request request) throws IOException {
		if(IS_ASYNC_CLIENT_YES.equals(isAsync)) {
			logger.info("invoke remote microservice by async retrofit client, the url is {}" , request.getUrl());
			return getAsyncHttpClient(request);
		} else {
			logger.info("invoke remote microservice by sync retrofit client, the url is {}" , request.getUrl());
			return getOkHttpClient(request);
		}
	}
	
	private Response getAsyncHttpClient(Request request) throws IOException {
		return new AsyncClient().execute(request);
	}
	
	private Response getOkHttpClient(Request request) throws IOException {
		return new OkClient().execute(request);
	}
}
