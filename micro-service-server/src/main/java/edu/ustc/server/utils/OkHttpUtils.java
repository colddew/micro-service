package edu.ustc.server.utils;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.util.CollectionUtils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class OkHttpUtils {
	
	public static final long CONNECT_TIMEOUT = 15;
	public static final long READ_TIMEOUT = 15;
	
	public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
	
	private static final OkHttpClient okHttpClient = new OkHttpClient();
	
	private OkHttpUtils() {
		okHttpClient.setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
		okHttpClient.setReadTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
	}
	
	public static Response execute(Request request) throws Exception {
		return okHttpClient.newCall(request).execute();
	}
	
	public static void enqueue(Request request) {
		
		okHttpClient.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response response) throws IOException {
				
			}
			
			@Override
			public void onFailure(Request Response, IOException exception) {
				
			}
		});
	}
	
	public static void enqueue(Request request, Callback responseCallback) {
		okHttpClient.newCall(request).enqueue(responseCallback);
	}
	
	public static String synGetString(String url) throws Exception {
		
		Request request = new Request.Builder().url(url).build();
		
		Response response = execute(request);
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new Exception("synGetString method fail, " + response);
		}
	}
	
	public static void asynDefaultGet(String url) throws Exception {
		Request request = new Request.Builder().url(url).build();
		enqueue(request);
	}
	
	public static void asynGet(String url, Callback responseCallback) throws Exception {
		Request request = new Request.Builder().url(url).build();
		enqueue(request, responseCallback);
	}
	
	public static String synPostJson(String url, String json) throws Exception {
		
		RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
		Request request = new Request.Builder().url(url).post(body).build();
		
		Response response = execute(request);
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new Exception("postJson method fail, " + response);
		}
	}
	
	public static String synPostForm(String url, Map<String, String> json) throws Exception {
		
		RequestBody body = makeFormEncodingBuilder(json).build();
		Request request = new Request.Builder().url(url).post(body).build();
		
		Response response = execute(request);
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("postForm method fail, " + response);
		}
	}
	
	private static FormEncodingBuilder makeFormEncodingBuilder(Map<String, String> json) {
		
		FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
		
		Set<Entry<String, String>> set = json.entrySet();
		if(!CollectionUtils.isEmpty(set)) {
			for(Entry<String, String> entry : set) {
				formEncodingBuilder.add(entry.getKey(), entry.getValue());
			}
		}
		
		return formEncodingBuilder;
	}
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		
//		String person = OkHttpUtils.synGetString("http://localhost:9001/person/1");
//		System.out.println(person);
//		
//		Map map = JSON.parseObject(person, Map.class);
//		System.out.println(map);
//		System.out.println(JSON.toJSONString(map));
		
//		OkHttpUtils.asynDefaultGet("http://localhost:9001/person/1");
		
//		OkHttpUtils.asynGet("http://localhost:9001/person/1", new Callback() {
//			
//			@Override
//			public void onResponse(Response response) throws IOException {
//				if (response.isSuccessful()) {
//					System.out.println(response.body().string());
//				}
//			}
//			
//			@Override
//			public void onFailure(Request request, IOException e) {
//				
//			}
//		});
		
//		String json = "{\"pid\":23,\"name\":\"test\",\"age\":20}";
//		System.out.println(synPostJson("http://localhost:9001/person", json));
		
//		@SuppressWarnings("serial")
//		Map<String, String> json = new HashMap<String, String>() {
//			{
//				this.put("pid", "24");
//				this.put("name", "test");
//				this.put("age", "20");
//			}
//		};
//		System.out.println(synPostForm("http://localhost:9001/person", json));
	}
}