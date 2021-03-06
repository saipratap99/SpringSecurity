package com.example.SpringSecurity.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.SpringSecurity.filters.JwtFillter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtFillter jwtFillter;
	
	// Authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/admin").hasRole("ADMIN")
			.antMatchers("/greet").hasAnyRole("USER","ADMIN")
			.antMatchers("/","index","/css/*","/js/*","/authenticate").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.sessionManagement() // saying to spring that don't use session
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS); 
		
		// instead use filters
		// jwtFilter is called before UsernamePasswordAuthenticationFilter is called
		http.addFilterBefore(jwtFillter, UsernamePasswordAuthenticationFilter.class);
		
	}

	// We need to authorize requests, any request should be authenticated and authentication type is basic auth (we need pass username and password everytime)
	
	// antMatcher finds the pattern of mentioned string and allows everyone to that path
	
	// Authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/* InMemoryAuthentication
		
		auth.inMemoryAuthentication()
			.withUser("pratap")
			.password("changeme")                           
			.roles("USER")
			.and() // and helps to get state of inMemoryAuth
			.withUser("admin")
			.password("admin")
			.roles("ADMIN");
		
		*/
		
		/* JPA */
		 auth.userDetailsService(userDetailsService);
	
		
	}	
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	

	
}
