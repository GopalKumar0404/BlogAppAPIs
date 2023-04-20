package com.gopal.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Component
public class UserDto {
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public Set<RoleDto> getRoles() {
		return roles;
	}

	@JsonProperty
	public void setRoles(Set<RoleDto> roles) {
		this.roles = roles;
	}

	
	private int id;
	@NotEmpty
	@Size(min=3, max=15,message="Name length must be min of 3 chars and max of 15 chars")
	private String name;
	@NotEmpty
	@Size(min=8, max=15,message="Password length must be min of 7 chars and max of 15 chars")
	private String password;
	@Email
	@Column(unique = true)
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	@Size(min=10, max=150,message="About length must be min of 10 chars and max of 150 chars")
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();
	
}
