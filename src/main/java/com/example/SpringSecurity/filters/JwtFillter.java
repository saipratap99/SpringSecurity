package com.example.SpringSecurity.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.SpringSecurity.utils.JwtUtil;

import net.bytebuddy.asm.MemberSubstitution.Substitution.Chain;

// this filter need to intercept once per request
@Component
public class JwtFillter extends OncePerRequestFilter{

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String authoriazationHeader = request.getHeader("Authorization"); // Bearer + jwt
		
		String username = null;
		String jwt = null;
		if(authoriazationHeader != null && authoriazationHeader.startsWith("Bearer ")) {
			jwt = authoriazationHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}
		
		// if no security context is authenticated
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if(jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities()
						);
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
						
			}
			
			// then handover to next filter
			filterChain.doFilter(request, response);
		}
	}

}
