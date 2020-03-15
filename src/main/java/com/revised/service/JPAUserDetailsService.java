package com.revised.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.revised.model.User;
import com.revised.repository.UserRepository;

@Service(value = "jpaUserDetailsService")
public class JPAUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> user = userRepository.findByUsername(username);
		if(user.isPresent()) {
			List<SimpleGrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(user.get().getRoles()));
			return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
					user.get().getPassword(), roles);
		}
		return null;
	}

}
