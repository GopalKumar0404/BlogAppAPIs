package com.gopal.blog.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gopal.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
