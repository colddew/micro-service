package edu.ustc.server.utils;

import okhttp3.*;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class OkHttpUtils {

	public static final long CONNECT_TIMEOUT = 15;
	public static final long WRITE_TIMEOUT = 15;
	public static final long READ_TIMEOUT = 15;

	public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

	private static OkHttpClient okHttpClient;

	static {
		okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
				.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
				.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
				.build();
	}
	
	public static Response execute(Request request) throws Exception {
		return okHttpClient.newCall(request).execute();
	}
	
	public static void enqueue(Request request) {
		
		okHttpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call call, Response response) throws IOException {

			}

			@Override
			public void onFailure(Call call, IOException e) {

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
	
	public static String synPostForm(String url, Map<String, String> map) throws Exception {
		
		RequestBody body = makeFormBodyBuilder(map).build();
		Request request = new Request.Builder().url(url).post(body).build();
		
		Response response = execute(request);
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("postForm method fail, " + response);
		}
	}
	
	private static FormBody.Builder makeFormBodyBuilder(Map<String, String> map) {
		
		FormBody.Builder formBodyBuilder = new FormBody.Builder();
		
		Set<Entry<String, String>> set = map.entrySet();
		if(!CollectionUtils.isEmpty(set)) {
			for(Entry<String, String> entry : set) {
				formBodyBuilder.add(entry.getKey(), entry.getValue());
			}
		}
		
		return formBodyBuilder;
	}
	
//	public static void main(String[] args) throws Exception {
//
//		String person = OkHttpUtils.synGetString("http://localhost:9001/person/1");
//		System.out.println(person);
//
//		Map map = JSON.parseObject(person, Map.class);
//		System.out.println(map);
//		System.out.println(JSON.toJSONString(map));
//
//		OkHttpUtils.asynDefaultGet("http://localhost:9001/person/1");
//
//		OkHttpUtils.asynGet("http://localhost:9001/person/1", new Callback() {
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				if (response.isSuccessful()) {
//					System.out.println(response.body().string());
//				}
//			}
//
//			@Override
//			public void onFailure(Call call, IOException e) {
//
//			}
//		});
//
//		String json = "{\"pid\":23,\"name\":\"test\",\"age\":20}";
//		System.out.println(synPostJson("http://localhost:9001/person", json));
//
//		@SuppressWarnings("serial")
//		Map<String, String> hashMap = new HashMap<String, String>() {
//			{
//				this.put("pid", "24");
//				this.put("name", "test");
//				this.put("age", "20");
//			}
//		};
//		System.out.println(synPostForm("http://localhost:9001/person", hashMap));
//	}
}