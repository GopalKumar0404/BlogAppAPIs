package com.gopal.blog.services;

import java.util.List;

import com.gopal.blog.payloads.PostDto;
import com.gopal.blog.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	PostDto updatePost(PostDto postDto, Integer postId);
	void deletePost(Integer postId);
	PostResponse getAllPosts(Integer pageNumber,Integer pageSize, String sortBy, String sortByDirection);
	PostDto getPostById(Integer postId);
	// get all posts by Category
	List<PostDto> getPostsByCategory(Integer categoryId);
	// get all posts by User
	List<PostDto> getPostsByUser(Integer userId);
	// Search Post
	List<PostDto> searchPosts(String keyword);
}
