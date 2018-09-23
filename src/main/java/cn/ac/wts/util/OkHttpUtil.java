package cn.ac.wts.util;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;

import cn.wts.entity.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Part;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.FormBody.Builder;
import okhttp3.Headers;
 
public class OkHttpUtil {
	public static final MediaType FORM_CONTENT_TYPE= MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
	//json数据
	private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
	//png数据
	private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
	//markdown数据
	private static final MediaType MEDIA_TYPE_MARKDOWN= MediaType.parse("text/x-markdown; charset=utf-8");
	//二进制数据
	private static final MediaType MEDIA_TYPE_FILE = MediaType.parse("application/octet-stream");
	private static  OkHttpClient mOkHttpClient = null;
    static{
    	if(mOkHttpClient==null){
    		mOkHttpClient = new OkHttpClient()
					.newBuilder()
					.connectTimeout(30, TimeUnit.SECONDS)
	                .readTimeout(20, TimeUnit.SECONDS)
					.build();
    	}
    }
    
    public static void printResponseInfo(Response response)throws Exception{
    	if (response.isSuccessful()) { 
    		int code= response.code();
			String body=response.body().string();
			System.out.println("success code: " + code);
			System.out.println("success body: " + body);
			
			//提取响应头
			Headers responseHeaders = response.headers();
			// 这个循环遍历的方法很有意思，用的name,value
			for (int i = 0; i < responseHeaders.size(); i++) {
				System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
			}
			//使用header(name)会返回一个string
			System.out.println("Server: " + response.header("Server"));
		    System.out.println("Date: " + response.header("Date"));
		    //使用headers(name)会返回一个string list
		    System.out.println("Vary: " + response.headers("Vary"));
		    
    	}else{
    		System.out.println("fail code: " + response.code());
			System.out.println("fail body: " + response.body().string());
			throw new IOException("Unexpected code " + response);
    	}
    }
    
    public static Response getByOkHttpClient(String url,Map<String,String> paramMap) throws IOException{
    	if(paramMap!=null &&paramMap.size()>0){
    		//参数不为空时,向url中加入参数sss?1=1&a=1&b=2
    		url +="?1=1";
    		for(String paramKey:paramMap.keySet()){
    			url +="&"+paramKey+"="+paramMap.get(paramKey);
    		}
    	}
    	Request request = new Request.Builder().url(url).build();
    	return execute(request); 
    }
    
    /**
     * okhttp Post方式提交json数据
     * @param url
     * @param paramJson
     * @return
     * @throws IOException
     */
    
    public static Response postJsonByOkHttpClient(String url,String paramJson) throws IOException{
    	RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, paramJson);
    	Request request = new Request.Builder().url(url)
    			.post(body).build();
    	return execute(request); 
    }
    
    /**
     * okhttp Post方式提交表单(有BUG,一直乱码)
     * @param url
     * @param paramMap
     * @return
     * @throws IOException
     */
    
    public static Response postFormByOkHttpClient(String url,Map<String,String> paramMap) throws IOException{
    	/**
    	 * okhttp3.FormBody instead of FormEncodingBuilder.
    	 * （OkHttp3.x中原来2.x里的FormEncodingBuilder已被FormBody取代）
    	 */
    	Charset charset = Charset.forName(StandardCharsets.UTF_8.name());
    	//这个垃圾FormBody不知道为啥一直乱码
    	Builder builder=new FormBody.Builder(charset);
		for(String paramKey:paramMap.keySet()){
			builder.add(paramKey, paramMap.get(paramKey)); 
		} 
		RequestBody body = builder.build();
		Request request = new Request.Builder().url(url).post(body).build();
		return execute(request); 
    }
    
    /**
     * okhttp Post方式提交表单(通过FORM_CONTENT_TYPE防止中文乱码)
     * @param url
     * @param paramMap
     * @return
     * @throws IOException
     */
    public static Response postFormContTypeByOkHttpClient(String url,Map<String,String> paramMap) throws IOException{
    	  StringBuffer sb = new StringBuffer();
    	  for (String key: paramMap.keySet()) {
              sb.append(key+"="+paramMap.get(key)+"&");
          }
    	RequestBody body = RequestBody.create(FORM_CONTENT_TYPE, sb.toString());
		Request request = new Request.Builder().url(url).post(body).build();
		return execute(request); 
    }
    
    /**
     * okhttp post方式提交markdown文件
     * @param url
     * @return
     */
    public static Response postMarkDownFile(String url,String mdFilePath) throws IOException{
    	File file =new File(mdFilePath);
    	if(file==null ||!file.exists()|| !file.getName().endsWith(".md")){
    		System.out.println("file not exist or file format is not correct");
    		return null;
    	} 
    	Request request = new Request.Builder()
    	        .url(url)
    	        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
    	        .build();
    	return execute(request); 
    }
    /**
     * okhttp post方式提交文件(每次上传一个文件)
     * @param url	后台接受路径
     * @param filePath	上传文件路径
     * @param paramFile	上传文件后台接收参数名
     * @param paramMap	上传文件后台其他参数map
     * @return
     * @throws IOException
     */
    public static Response postFile(String url,String filePath,String paramFile,Map<String,String> paramMap) throws IOException{
    	File file =new File(filePath);
    	if(file==null ||!file.exists()){
    		System.out.println("file not exist or file format is not correct");
    		return null;
    	} 
    	RequestBody fileBody = RequestBody.create(MEDIA_TYPE_FILE, file);
    	MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
    	 for (String key: paramMap.keySet()) {
             builder.addFormDataPart(key,paramMap.get(key));
         }
    	builder.addFormDataPart(paramFile, file.getName(), fileBody);
        Request request = new Request.Builder()
    	        .url(url)
    	        .post(builder.build())
    	        .build();
        return execute(request); 
    }
    
    /**
     * okhttp post方式提交文件(同时上传多个文件)
     * @param url	后台接受路径
     * @param filePathList	上传文件路径list
     * @param paramFile	上传文件后台接收参数名
     * @param paramMap	上传文件后台其他参数map
     * @return
     * @throws IOException
     */
    public static Response postFileList(String url,List<String> filePathList,String paramFile,Map<String,String> paramMap) throws IOException{
    	MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
    	 for (String key: paramMap.keySet()) {
             builder.addFormDataPart(key,paramMap.get(key));
         }
    	 for(String pathList:filePathList){
    		 File file = new File(pathList);
    		 builder.addFormDataPart(paramFile, file.getName(), RequestBody.create(MEDIA_TYPE_FILE, file));
     	 }
        Request request = new Request.Builder()
    	        .url(url)
    	        .post(builder.build())
    	        .build();
        return execute(request); 
    }
    /**
     * 同步线程访问
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException{
        return mOkHttpClient.newCall(request).execute();
    }
    /**
     * 开启异步线程访问
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, Callback responseCallback){
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }
    /**
     * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
     * @param request
     */
    public static void enqueue(Request request){
        mOkHttpClient.newCall(request).enqueue(new Callback() {
			public void onFailure(Call call, IOException e) {
				// TODO Auto-generated method stub
				
			}
			public void onResponse(Call call, Response response) throws IOException {
				// TODO Auto-generated method stub
			}
            
        });
    }
    
}
