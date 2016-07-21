package edu.ustc.gateway.config;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
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
	
	@Override
	public Response execute(Request request) throws IOException {
		
		AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
		
		try {
			
			Future<org.asynchttpclient.Response> future = asyncHttpClient.prepareGet(request.getUrl()).execute();
			org.asynchttpclient.Response response = future.get();
			
			List<Header> headers = new ArrayList<Header>();
			List<Entry<String, String>> entries = response.getHeaders().entries();
			for(Entry<String, String> entry : entries) {
				headers.add(new Header(entry.getKey(), entry.getValue()));
			}
			
			String mimeType = response.getContentType();
			byte[] bytes = response.getResponseBodyAsBytes();
			long length = bytes.length;
			BufferedInputStream stream = new BufferedInputStream(new ByteArrayInputStream(bytes));
			AsyncInputStream body = new AsyncInputStream(mimeType, length, stream);
			
			return new Response(request.getUrl(), response.getStatusCode(), response.getStatusText(), headers, body);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			if(null != asyncHttpClient) {
				asyncHttpClient.close();
			}
		}
		
		
		return null;
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
