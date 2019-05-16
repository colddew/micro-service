package edu.ustc.gateway.service.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoggingInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

	@Override
	public Response intercept(Chain chain) throws IOException {

		logger.info("invoke okhttp3 LoggingInterceptor...");

		Request request = chain.request();

		long t1 = System.nanoTime();
		logger.info(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));

		Response response = chain.proceed(request);

		long t2 = System.nanoTime();
		logger.info(String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));

		return response;
	}
}
