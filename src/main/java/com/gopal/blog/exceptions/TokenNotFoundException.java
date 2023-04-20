package com.gopal.blog.exceptions;

public class TokenNotFoundException extends RuntimeException {

	String message;

	public TokenNotFoundException(String message) {
		super(String.format("Bad Request, %s", message));
		this.message = message;

	}

}
