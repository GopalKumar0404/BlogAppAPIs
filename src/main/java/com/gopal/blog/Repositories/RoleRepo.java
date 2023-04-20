package com.gopal.blog.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gopal.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
