package com.gopal.blog.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gopal.blog.exceptions.UserDetailsNotFoundException;
import com.gopal.blog.payloads.JwtAuthRequest;
import com.gopal.blog.payloads.JwtAuthResponse;
import com.gopal.blog.payloads.UserDto;
import com.gopal.blog.security.JwtTokenHelper;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	private String token;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest){
	
		this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
		this.token = this.jwtTokenHelper.generateToken(userDetails);
		UserDto user = this.modelMapper.map(userDetails, UserDto.class);
		return new ResponseEntity<JwtAuthResponse>(new JwtAuthResponse(token,user), HttpStatus.OK);
	}

	private void authenticate(String username, String password) {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details");
			throw new UserDetailsNotFoundException(username);
		}
				
	}
}
