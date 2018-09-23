package cn.ac.wts.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.ac.wts.util.OkHttpUtil;
import cn.wts.entity.User;
import okhttp3.Response;

public class TestOkHttpUtil {
	  public static void main(String[] args) {
			try {
				//Map<String,String>paramMap =new HashMap<String,String>();
				//paramMap.put("userName", "wts111");
				//paramMap.put("passWord", "wts111密码");
				//User user=new User();
				//user.setUserName("wts");
				//user.setPassWord("wts密码123456");
				//Response response=OkHttpUtil.getByOkHttpClient("http://localhost:8080/springDemo/strInterFace", paramMap);
				//Response response2=OkHttpUtil.postJsonByOkHttpClient("http://localhost:8080/springDemo/jsonInterFace", JSON.toJSONString(user));
				//Response formResponse1=OkHttpUtil.getByOkHttpClient("http://localhost:8080/springDemo/formInterFace", paramMap);
				//Response formResponse2=OkHttpUtil.postFormByOkHttpClient("http://localhost:8080/springDemo/formInterFace", paramMap);
				//Response formResponse2=OkHttpUtil.postFormContTypeByOkHttpClient("http://localhost:8080/springDemo/formInterFace", paramMap);
				
				//Response mdResponse=OkHttpUtil.postMarkDownFile("https://api.github.com/markdown/raw","f://README.md");
				//OkHttpUtil.printResponseInfo(mdResponse);
				
				Map<String,String>paramMap =new HashMap<String,String>();
				paramMap.put("hash", "hash_"+UUID.randomUUID().toString());
				paramMap.put("timestamp", "timestamp_"+System.currentTimeMillis());
//				OkHttpUtil.postFile(
//							"http://localhost:8080/springDemo/fileInterface", 
//							"f://zhifubao.png",
//							"springFile",
//							paramMap);
//				OkHttpUtil.postFile(
//						"http://localhost:8080/springDemo/fileInterface", 
//						"f://loading.gif",
//						"springFile",
//						paramMap);
				List<String> fileStrList=new ArrayList<String>();
				fileStrList.add("f://zhifubao.png");
				fileStrList.add("f://loading.gif");
				OkHttpUtil.postFileList(
						"http://localhost:8080/springDemo/fileInterface", 
						fileStrList,
						"springFile",
						paramMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
