package cn.ac.wts.spring;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	 * 访问http://localhost:8080/springDemo默认进入defaultMethod方法
	 * @return
	 */
	@RequestMapping() 
	public @ResponseBody String defaultMethod() {  
        return "This is a default method for the class";  
    }  
	
	@ResponseBody
	@RequestMapping(value = "strInterFace", method = RequestMethod.GET)
	public String strInterFace(String str){
		System.out.println("str: "+str);
		return "str接口success";
	} 
	
	/**
	 * RequestParam 注解配合 RequestMapping 一起使用，可以将请求的参数同处理方法的参数绑定在一起
	 * RequestParam中required默认是true,required设为false时可以对两个 URL 都会进行处理：
	 * 		http://localhost:8080/springDemo/strInterFaceWithRequestParam
	 * 		http://localhost:8080/springDemo/strInterFaceWithRequestParam?str=111
	 * @param str
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "strInterFaceWithRequestParam", method = RequestMethod.GET)
	public String strInterFaceWithRequestParam(@RequestParam(value = "str",required = false ,defaultValue = "defaultStr") String str){
		System.out.println("str: "+str);
		return "strRequestParam接口success";
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
		System.out.println("hash: "+hash);
		System.out.println("timestamp: "+timestamp);
		System.out.println("file size: "+file.getSize()); 
		System.out.println("file form name: "+file.getName()); 
		System.out.println("file real name: "+file.getOriginalFilename()); 
		return "file upload success";
	}
	
	
	  @GetMapping("/user")  
	    public @ResponseBody ResponseEntity< User >  getUser() {  
	    	User user=new User("wts","wtsPwd");
	        return new ResponseEntity<User>(user,HttpStatus.OK);
	   }   
	    
    @GetMapping("/userList")  
    public @ResponseBody ResponseEntity< List<User> >  getuserList() {  
    	List<User> userList =new ArrayList<User>();
    	for(int i=0;i<5;i++){
    		userList.add(new User("wts_"+i,"wtsPwd_"+i));
    	}
        return new ResponseEntity<List<User>>(userList,HttpStatus.OK);
    }
	
    @GetMapping("/person")  
    public @ResponseBody ResponseEntity < String > getPerson() {  
        return new ResponseEntity < String > ("Response from GET", HttpStatus.OK);  
    }  
    
    /**
     * RequestMapping(get方式的GetMapping等同) 来处理多个 URI 
     * 中间用逗号隔开
     * 支持通配符以及ANT风格的路径
     * @return
     */
    @GetMapping(value = {  
            "page",  			//http://localhost:8080/springDemo/page
            "page*",  			//http://localhost:8080/springDemo/pageabc
            "view/*",			//http://localhost:8080/springDemo/view/abc
            "**/msg"  			//http://localhost:8080/springDemo/abc/msg
        })  
    public @ResponseBody ResponseEntity < String > getMultiPerson() {  
        return new ResponseEntity < String > ("Response MultiPerson from GET", HttpStatus.OK);  
    }  
    
    
    @GetMapping("/person/{id}")  
    public @ResponseBody ResponseEntity < String > getPersonById(@PathVariable String id) {  
        return new ResponseEntity < String > ("Response from GET with id " + id, HttpStatus.OK);  
    }  
    @PostMapping("/person")  
    public @ResponseBody ResponseEntity < String > postPerson() {  
        return new ResponseEntity < String > ("Response from POST method", HttpStatus.OK);  
    }  
    @PutMapping("/person")  
    public @ResponseBody ResponseEntity < String > putPerson() {  
        return new ResponseEntity < String > ("Response from PUT method", HttpStatus.OK);  
    }  
    @DeleteMapping("/person")  
    public @ResponseBody ResponseEntity < String > deletePerson() {  
        return new ResponseEntity < String > ("Response from DELETE method", HttpStatus.OK);  
    }  
    @PatchMapping("/person")  
    public @ResponseBody ResponseEntity < String > patchPerson() {  
        return new ResponseEntity < String > ("Response from PATCH method", HttpStatus.OK);  
    }  
}
