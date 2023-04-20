package com.gopal.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gopal.blog.Repositories.RoleRepo;
import com.gopal.blog.Repositories.UserRepo;
import com.gopal.blog.config.AppConstants;
import com.gopal.blog.entities.Role;
import com.gopal.blog.entities.User;
import com.gopal.blog.exceptions.ResourceNotFoundException;
import com.gopal.blog.payloads.UserDto;
import com.gopal.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = new User();
		user = this.dtoToUser(userDto);
		 
		User createdUser = new User();
		createdUser = this.userRepo.save(user);
		
		UserDto updatedUserDto = new UserDto();
		updatedUserDto = userToDto(createdUser);
		return updatedUserDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		
		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		UserDto userDto = this.userToDto(user);
		return userDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user-> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","Id",userId));
		this.userRepo.delete(user);
				
	}
	
	
	
	public User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
//		user.setId(userDto.getId());
//		user.setAbout(userDto.getAbout());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setName(userDto.getName());
		
		return user;
	}
	
	public UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		userDto.setAbout(user.getAbout());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
//		userDto.setEmail(user.getEmail());
//		userDto.setId(user.getId());
		return userDto;
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		User savedUser = this.userRepo.save(user);
		
		return this.modelMapper.map(savedUser, UserDto.class);
	}

}
