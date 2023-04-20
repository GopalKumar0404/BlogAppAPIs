package com.gopal.blog.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gopal.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	
}
