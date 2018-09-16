package cn.ac.wts.spring;


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
	
    @GetMapping("/person")  
    public @ResponseBody ResponseEntity < String > getPerson() {  
        return new ResponseEntity < String > ("Response from GET", HttpStatus.OK);  
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
