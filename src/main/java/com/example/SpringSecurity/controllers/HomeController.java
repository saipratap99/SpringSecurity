package com.example.SpringSecurity.controllers;

import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.SpringSecurity.models.AuthenticationRequest;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String home() {
		return "home.jsp";
	}
	
	@RequestMapping("/greet")
	public String greet() {
		return "hello.jsp";
	}
	
	@RequestMapping("/admin")
	public String admin() {
		return "admin.jsp";
	}
	
	@RequestMapping("/authenticate")
	public RequestEntity<T> generateToken(@RequestBody AuthenticationRequest req){
		
	}
}
