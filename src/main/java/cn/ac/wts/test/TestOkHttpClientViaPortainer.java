package cn.ac.wts.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.FormBody.Builder;

public class TestOkHttpClientViaPortainer {
	//private static final OkHttpClient client = new OkHttpClient();
	private static final OkHttpClient client = new OkHttpClient()
												.newBuilder()
												.connectTimeout(10, TimeUnit.SECONDS)
								                .readTimeout(20, TimeUnit.SECONDS)
												.build();
	
	private static String bear="";
	
	public static void main(String[] args) throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		params.put("Username", "admin");
		params.put("Password", "admin123");
		postByOkHttpClient("http://192.168.99.100:9000/api/auth", JSON.toJSONString(params), "");
//		getByOkHttpClient("http://192.168.99.100:9000/api/endpoints/1/docker/containers/json", bear);
		
		//String jsonParm=FileUtils.readFileToString(new File(TestOkHttpClient.class.getClass().getResource("/testCreate.json").getPath()));
		//System.out.println("jsonParm: "+jsonParm); 
		//postByOkHttpClient("http://192.168.99.100:9000/api/endpoints/1/docker/containers/create?name=tomcatTest", 
		//		jsonParm, bear);
		
//		params.put("login", "root");
//		params.put("password", "12345678");
		
//		postByOkHttpClient("http://192.168.99.100/api/v3"+"/session", 
//					JSON.toJSONString(params)); 
		
//		postFormByOkHttpClient("http://192.168.99.100/api/v3"+"/session", 
//						params); 
		
	}
	
	 
	public static void callHttpRequest(Request request) throws IOException {
		System.out.println("call request...");
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) { 
			//System.out.println("response str: "+response.body().toString());
			int code= response.code();
			String body=response.body().string();
			System.out.println("success code: " + code);
			System.out.println("success body: " + body);
			if(bear==null||bear.equals("")){
				bear=(String)JSON.parseObject(body, Map.class).get("jwt");
				//bear=(String)JacksonUtils.jsonStringToObject(body, Map.class).get("jwt");
			} 
			Headers responseHeaders = response.headers();
			// 这个循环遍历的方法很有意思，用的name,value
			for (int i = 0; i < responseHeaders.size(); i++) {
				//System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
			}
		} else {
			System.out.println("fail code: " + response.code());
			System.out.println("fail body: " + response.body().string());
			throw new IOException("Unexpected code " + response);
		} 
	}

	public static void getByOkHttpClient(String url, String bear) throws Exception {
		Request request = new Request.Builder().url(url).addHeader("Authorization", bear).build();
		callHttpRequest(request);
	}

	public static void postByOkHttpClient(String url, String json, String bear) throws Exception {
		//System.out.println("json: " + json);
		MediaType media_Json = MediaType.parse("application/json; charset=utf-8");
		RequestBody body = RequestBody.create(media_Json, json);
		Request request = new Request.Builder().url(url).addHeader("Authorization", bear).post(body).build();
		callHttpRequest(request);
	}
	
	public static void postByOkHttpClient(String url, String json) throws Exception {
		//System.out.println("json: " + json);
		MediaType media_Json = MediaType.parse("application/json; charset=utf-8");
		RequestBody body = RequestBody.create(media_Json, json);
		Request request = new Request.Builder().url(url).post(body).build();
		callHttpRequest(request);
	}
	
	public static void postFormByOkHttpClient(String url, Map<String,String> jsonMap) throws Exception {
		Builder builder=new FormBody.Builder();
		for(String key:jsonMap.keySet()){
			builder.add(key, jsonMap.get(key));
		}
		RequestBody body = builder.build();
		Request request = new Request.Builder().url(url).post(body).build();
		callHttpRequest(request);
		 /*
	  		RequestBody formBody = 
			 //new FormEncodingBuilder()
			 //https://github.com/square/okhttp，OkHttp3.x与2.x不同之处见CHANGELOG.md
			 //okhttp3.FormBody instead of FormEncodingBuilder.（OkHttp3.x，FormEncodingBuilder已被FormBody取代）
		 	 new FormBody.Builder()
		    .add("platform", "android")
		    .add("name", "bug")
		    .add("subject", "XXXXXXXXXXXXXXX")
		    .build();
		  */
	}
}
