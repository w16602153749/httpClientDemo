package cn.ac.wts.util;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
 
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
    
    
    public static Response getByOkHttpClient(String url) throws IOException{
    	Request request = new Request.Builder().url(url).build();
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
