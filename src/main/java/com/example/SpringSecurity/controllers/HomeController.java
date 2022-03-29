package com.example.SpringSecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
