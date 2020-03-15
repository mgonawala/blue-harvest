package com.revised.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revised.dto.AuthenticateDto;
import com.revised.dto.AuthenticationResponse;
import com.revised.util.JwtUtil;

@RestController
public class AuthenticationController {
	
	@Autowired
	private JwtUtil util;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("jpaUserDetailsService")
	private UserDetailsService userDetailsService;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticateDto authenticateRequest) throws Exception {
		try {

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticateRequest.getUsername(), authenticateRequest.getPassword()));
		} catch (BadCredentialsException exception) {
			throw new Exception("Bad username or password", exception);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticateRequest.getUsername());
		final String token = util.geneateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
	 
}
