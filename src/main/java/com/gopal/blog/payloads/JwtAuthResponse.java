package com.gopal.blog.payloads;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthResponse {

	private String token;
	private UserDto user;
	
}
