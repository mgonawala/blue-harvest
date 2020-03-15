package com.revised.dto;

public class AuthenticateDto {

	public String username;
	
	public String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthenticateDto(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public AuthenticateDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
