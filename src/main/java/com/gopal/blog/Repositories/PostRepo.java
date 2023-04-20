package com.gopal.blog.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gopal.blog.entities.Category;
import com.gopal.blog.entities.Post;
import com.gopal.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String keyword);
}
