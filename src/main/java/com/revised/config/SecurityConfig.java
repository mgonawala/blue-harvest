package com.revised.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.revised.filter.JwtRequestFilter;
import com.revised.service.JPAUserDetailsService;
import com.revised.service.MyUserDetailsService;

/**
 * 
 * This configuration class is an example of BasiAuthentication.
 * Any request coming in will have to provide username and password as Authorization header to get access.
 * 
 * @author mgonawal
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired private MyUserDetailsService myUserDetailsService;
	
	@Autowired private JPAUserDetailsService jPAUserDetailsService;
	  
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jPAUserDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
				.antMatchers("/api/v1/**accounts**").hasAnyRole("ADMIN")
				.antMatchers("/api/v1/customers").hasAnyRole("USER","ADMIN")
				.antMatchers("/api/v1/transactions**").hasAnyRole("USER","ADMIN")
				.antMatchers("/authenticate").permitAll().
				anyRequest().permitAll()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				;
		http.addFilterBefore(jwtRequestFilter	, UsernamePasswordAuthenticationFilter.class);
	}	
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	  
	  @Bean
	  
	  @Override protected AuthenticationManager authenticationManager() throws
	  Exception { return super.authenticationManager(); }
	 
}
