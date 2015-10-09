package edu.ustc.gateway.service;

import retrofit.RequestInterceptor;

public class SessionRequestInterceptor implements RequestInterceptor {
	
	@Override
	public void intercept(RequestFacade request) {
		System.out.println("add request params...");
	}
}
