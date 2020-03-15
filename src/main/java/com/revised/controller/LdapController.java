package com.revised.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LdapController {

	@GetMapping("/ldap")
	public String hello() {
		return "hello world";
	}
}
