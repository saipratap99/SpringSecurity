package com.example.SpringSecurity.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter{

	// Authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/","index","/css/*","/js/*").permitAll().and().authorizeRequests()
			.antMatchers("/greet").hasAnyRole("USER","ADMIN").and().authorizeRequests()
			.antMatchers("/admin").hasRole("ADMIN")
			.anyRequest()
			.authenticated()
			.and()
			.formLogin();
	}

	// We need to authorize requests, any request should be authenticated and authentication type is basic auth (we need pass username and password everytime)
	
	// antMatcher finds the pattern of mentioned string and allows everyone to that path
	
	// Authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("pratap")
			.password("changeme")                           
			.roles("USER")
			.and() // and helps to get state of inMemoryAuth
			.withUser("admin")
			.password("admin")
			.roles("ADMIN");
		
		
	}	
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	
}
