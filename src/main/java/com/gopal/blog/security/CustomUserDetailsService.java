package com.gopal.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gopal.blog.Repositories.UserRepo;
import com.gopal.blog.entities.User;
import com.gopal.blog.exceptions.ResourceNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User","Email"+username,0));
	
		return user;
	}

}
