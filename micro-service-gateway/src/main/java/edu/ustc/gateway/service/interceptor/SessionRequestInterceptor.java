package edu.ustc.gateway.service.interceptor;

import retrofit.RequestInterceptor;

public class SessionRequestInterceptor implements RequestInterceptor {
	
	@Override
	public void intercept(RequestFacade request) {
		System.out.println("add request params...");
	}
}
