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
	
	public static final Integer IS_ASYN_CLIENT_YES = 1;
	public static final Integer IS_ASYN_CLIENT_NO = 0;
	
	private Integer isAsyn;
	
	public Integer getIsAsyn() {
		return isAsyn;
	}
	
	public void setIsAsyn(Integer isAsyn) {
		this.isAsyn = isAsyn;
	}
	
	public MicroServiceClient(Integer isAsyn) {
		this.isAsyn = isAsyn;
	}
	
	@Override
	public Response execute(Request request) throws IOException {
		
		logger.info("invoke remote microservice by self-define retrofit client, the url is {}" , request.getUrl());
		
		if(IS_ASYN_CLIENT_YES.equals(isAsyn)) {
			return getAsyncHttpClient(request);
		} else {
			return getOkHttpClient(request);
		}
	}
	
	private Response getAsyncHttpClient(Request request) throws IOException {
		return null;
	}
	
	private Response getOkHttpClient(Request request) throws IOException {
		return new OkClient().execute(request);
	}
}
