package com.example.SpringSecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.SpringSecurity.models.AuthenticationRequest;
import com.example.SpringSecurity.models.AuthenticationResponse;
import com.example.SpringSecurity.utils.JwtUtil;

@Controller
public class HomeController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
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
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> generateToken(@RequestBody AuthenticationRequest req) throws Exception{
		
		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
				);
		}catch(BadCredentialsException e) {
			throw new Exception("Invalid username or password ",e);
			
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		 
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
		
	}
	
}
