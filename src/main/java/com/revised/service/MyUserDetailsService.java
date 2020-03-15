package com.revised.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService{

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub;
		return new User("mohini", "mohini",AuthorityUtils.createAuthorityList("ROLE_USER"));
	}

	
}
