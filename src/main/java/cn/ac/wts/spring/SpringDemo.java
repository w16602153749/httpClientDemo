package cn.ac.wts.spring;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.wts.entity.User;

@Controller
@RequestMapping("springDemo")
public class SpringDemo {
	/**
	 * 测试str接口
	 * @param str
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "strInterFace", method = RequestMethod.GET)
	public String strInterFace(String str){
		System.out.println("str: "+str);
		return "str接口success";
	} 
	
	@ResponseBody
	@RequestMapping(value = "jsonInterFace")
	public String jsonInterFace(@RequestBody User user){
		System.out.println("userName: "+user.getUserName());
		System.out.println("passWord: "+user.getPassWord()); 
		return "json接口success";
	} 
	
	@ResponseBody
	@RequestMapping(value = "formInterFace")
	public String formInterFace(User user){
		System.out.println("userName: "+user.getUserName());
		System.out.println("passWord: "+user.getPassWord()); 
		return "form接口success";
	} 
	
	@ResponseBody
	@RequestMapping(value = "fileInterface", method = RequestMethod.POST)
	public String charactersearch(
			@RequestParam(value = "springFile", required = true) MultipartFile file,String hash, String timestamp) {
		System.out.println("file: "+file.getSize()); 
		return "file upload success";
	}
}
