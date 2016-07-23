package edu.ustc.gateway.config;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

public class AsyncClient implements Client {
	
	private static AsyncHttpClient generateAsyncHttpClient() {
		AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
		return asyncHttpClient;
	}
	
	private final AsyncHttpClient client;
	
	public AsyncClient() {
		this(generateAsyncHttpClient());
	}
	
	public AsyncClient(AsyncHttpClient client) {
		if (client == null) throw new NullPointerException("client == null");
	    this.client = client;
	}
	
	@Override
	public Response execute(Request request) throws IOException {
		try {
			org.asynchttpclient.Response response = asyncExecute(request);
			return parseResponse(request, response);
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}
	
	private org.asynchttpclient.Response asyncExecute(Request request) throws InterruptedException, ExecutionException {
		Future<org.asynchttpclient.Response> future = client.prepareGet(request.getUrl()).execute();
		return future.get();
	}
	
	private Response parseResponse(Request request, org.asynchttpclient.Response response) {
		
		List<Header> headers = new ArrayList<Header>();
		response.getHeaders().forEach(header -> {
			headers.add(new Header(header.getKey(), header.getValue()));
		});
		
		byte[] bytes = response.getResponseBodyAsBytes();
		BufferedInputStream stream = new BufferedInputStream(new ByteArrayInputStream(bytes));
		AsyncInputStream body = new AsyncInputStream(response.getContentType(), (long) bytes.length, stream);
		
		return new Response(request.getUrl(), response.getStatusCode(), response.getStatusText(), headers, body);
	}
	
	private static class AsyncInputStream implements TypedInput {
		
	    private final String mimeType;
	    private final long length;
	    private final InputStream stream;
	    
	    private AsyncInputStream(String mimeType, long length, InputStream stream) {
	      this.mimeType = mimeType;
	      this.length = length;
	      this.stream = stream;
	    }
	    
	    @Override public String mimeType() {
	      return mimeType;
	    }
	    
	    @Override public long length() {
	      return length;
	    }
	    
	    @Override public InputStream in() throws IOException {
	      return stream;
	    }
	  }
}
