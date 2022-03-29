package com.example.SpringSecurity.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.SpringSecurity.daos.UserRepo;
import com.example.SpringSecurity.models.MyUserDetails;
import com.example.SpringSecurity.models.User;

//To inform AutoWired
@Service
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByEmail(username);
		user.orElseThrow(()-> new UsernameNotFoundException("User not found"));
		return new MyUserDetails(user.get());
	}

}
