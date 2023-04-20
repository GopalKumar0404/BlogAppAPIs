package com.gopal.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gopal.blog.Repositories.CategoryRepo;
import com.gopal.blog.Repositories.PostRepo;
import com.gopal.blog.Repositories.UserRepo;
import com.gopal.blog.entities.Category;
import com.gopal.blog.entities.Post;
import com.gopal.blog.entities.User;
import com.gopal.blog.exceptions.ResourceNotFoundException;
import com.gopal.blog.payloads.PostDto;
import com.gopal.blog.payloads.PostResponse;
import com.gopal.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private Post post;
	@Autowired
	private PostDto postDto;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private User user;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private Category category;
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		this.user = this.userRepo.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("User","Id",userId));
		this.category=this.categoryRepo.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("Category","Id",categoryId));
		
		this.post = this.modelMapper.map(postDto, Post.class);
		this.post.setImageName("default.png");
		this.post.setAddedDate(new Date());
		this.post.setCategory(category);
		this.post.setUser(user);
		this.post =this.postRepo.save(post);
		return this.modelMapper.map(this.post, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post p=this.postRepo.findById(postId).orElseThrow(()
				-> new ResourceNotFoundException("Post","Id",postId));
		
		this.post = this.modelMapper.map(postDto, Post.class);
		this.post.setAddedDate(new Date());
		if(this.post.getUser()==null)
			this.post.setUser(p.getUser());
		if(this.post.getCategory()==null)
			this.post.setCategory(p.getCategory());
		this.post.setPostId(postId);
		PostDto updatedPostDto = this.modelMapper.map(this.postRepo.save(this.post), PostDto.class);
		return updatedPostDto;
	}          

	@Override
	public void deletePost(Integer postId) {
		this.post=this.postRepo.findById(postId).orElseThrow(()
				-> new ResourceNotFoundException("Post","Id",postId));
		this.postRepo.delete(post);

	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy,String sortByDirection) {
		Sort sort = null;
		sort = sortByDirection.equalsIgnoreCase("ASC")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable p = PageRequest.of(pageNumber,pageSize,sort);
		Page<Post> pagePosts = this.postRepo.findAll(p);
		List<Post> posts = pagePosts.getContent();
		List<PostDto> postDtos = posts.stream().map(post
				-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//		List<PostDto> posts = this.postRepo.findAll().stream().map(post
//				-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setLastPage(pagePosts.isLast());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setPageSize(pagePosts.getSize());
		return postResponse;
	}
	
	

	@Override
	public PostDto getPostById(Integer postId) {
		this.post = this.postRepo.findById(postId).orElseThrow(()
				-> new ResourceNotFoundException("Post","Id",postId));
		this.postDto = this.modelMapper.map(post, PostDto.class);	
		return this.postDto;
	}
	

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		this.category = this.categoryRepo.findById(categoryId).orElseThrow(()
				-> new ResourceNotFoundException("Category","Id",categoryId));
//		List<Post> posts = this.postRepo.findByCategory(this.category);
		List<PostDto> postDtos = this.postRepo.findByCategory(this.category).stream().map(post 
				-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		this.user = this.userRepo.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("User","Id",userId));
		
			List<Post> posts = this.postRepo.findByUser(this.user);
			List<PostDto> postDtos = posts.stream().map(post-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
			return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		
		
		List<Post> posts =  this.postRepo.findByTitleContaining(keyword);
		
		List<PostDto> postDtos = posts.stream().map(post
				-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
