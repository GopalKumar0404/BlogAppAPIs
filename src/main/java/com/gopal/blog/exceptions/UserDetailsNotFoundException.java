package com.gopal.blog.exceptions;

public class UserDetailsNotFoundException extends RuntimeException {

	String username;
	
	
	public UserDetailsNotFoundException(String username) {
		super(String.format("Bad Credentials, Password does not match for user %s",username));
		this.username = username;
		
	}
}
