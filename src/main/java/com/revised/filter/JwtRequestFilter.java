package com.revised.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.revised.service.MyUserDetailsService;
import com.revised.util.JwtUtil;

import net.bytebuddy.asm.MemberSubstitution.Substitution.Chain;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

	@Autowired
	@Qualifier("jpaUserDetailsService")
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil util;
	/**
	 * 
	 * Examine the request for JWT token header.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authorizationHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;
		
		if( authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			username = util.extractUserName(jwt);
		}
		
		if( username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			if( util.validateToken(jwt, userDetails)) {
				
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null
						,userDetails.getAuthorities());
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(token);
			}
		}
		filterChain.doFilter(request, response);
	}

}
