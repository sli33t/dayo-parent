package cn.caishen.example.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.caishen.example.config.AuthUtils;

@RestController
public class AuthController {
	private Logger logger =  LogManager.getLogger(this.getClass().getName());
	
	@GetMapping("/product/{id}")
	public String getProduct(@PathVariable String id, HttpServletRequest req) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		
		logger.info("用户名等信息: " + JSON.toJSONString(authentication.getPrincipal()));
		logger.info("封装的传递信息: " + AuthUtils.getReqUser(req));
		
		return "get (Need Auth Request)product id : " + id;
	}
	
	@PostMapping("/product/{id}")
	@PreAuthorize("hasAnyAuthority('read')")
	public String postProduct(@PathVariable String id, HttpServletRequest req) {
		return "post (Need hasAnyAuthority)product id : " + id;
	}
 
	@GetMapping("/order/{id}")
	public String getOrder(@PathVariable String id) {
		return "(No Auth Request)order id : " + id;
	}
	
	@PostMapping("/user")
	public boolean addUser(@RequestBody JSONObject obj) throws UnsupportedEncodingException{
		//{"user":{"name":"aaa", "password":"bbb", "address":[{"item":"aaaaaaaaaaaa"}, {"item":"bbbbbbbbbbbbbb"}]}}
		logger.info("提交的json: "+obj);			
		JSONObject user = obj.getJSONObject("user");		
		
		//数据库应当保存加密后的密码
        BCryptPasswordEncoder passwordEncode = new BCryptPasswordEncoder();
        String name =  user.getString("name".toLowerCase());
        String pwd = user.getString("password".toLowerCase());
        String encodePwd = passwordEncode.encode(pwd);   
  
        logger.info(String.format("用户注册: 用户名 %s, 密码 %s, 密码加密后 %s", name, pwd, encodePwd));
        
        //TODO 
        //保存到数据库
        
		return true;
	}		
}
