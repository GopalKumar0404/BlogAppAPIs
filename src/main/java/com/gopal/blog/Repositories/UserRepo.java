package com.gopal.blog.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gopal.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);
}
