package cn.ac.wts.util;

import java.io.IOException;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.FormBody.Builder;
 
public class OkHttpUtil {
	//json数据
	private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
	//png数据
	private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
	//markdown数据
	public static final MediaType MEDIA_TYPE_MARKDOWN= MediaType.parse("text/x-markdown; charset=utf-8");
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
     * okhttp Post方式提交表单
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
    	Builder builder=new FormBody.Builder();
		for(String paramKey:paramMap.keySet()){
			builder.add(paramKey, paramMap.get(paramKey));
		}
		RequestBody body = builder.build();
		Request request = new Request.Builder().url(url).post(body).build();
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
    
    public static void main(String[] args) {
		try {
			Map<String,String>paramMap =new HashMap<String,String>();
			paramMap.put("userName", "wts");
			paramMap.put("passWord", "wtspwd");
			User user=new User();
			user.setUserName("wts");
			user.setPassWord("123456");
			//Response response=getByOkHttpClient("http://localhost:8080/springDemo/strInterFace", paramMap);
			Response response2=postJsonByOkHttpClient("http://localhost:8080/springDemo/jsonInterFace", JSON.toJSONString(user));
			printResponseInfo(response2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
