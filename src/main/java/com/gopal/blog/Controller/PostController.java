package com.gopal.blog.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gopal.blog.config.AppConstants;
import com.gopal.blog.entities.Post;
import com.gopal.blog.payloads.ApiResponse;
import com.gopal.blog.payloads.PostDto;
import com.gopal.blog.payloads.PostResponse;
import com.gopal.blog.services.FileService;
import com.gopal.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired	
	private PostService postService;
	@Autowired
	Post post;
	@Autowired
	PostDto postDto;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;
	
	
	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId, @PathVariable Integer categoryId){
		this.postDto = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(this.postDto,HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable Integer userId,
			@RequestParam (value ="pageNumber", defaultValue =AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, 
			@RequestParam (value ="pageSize", defaultValue =AppConstants.PAGE_SIZE, required = false) Integer pageSize){
		List<PostDto> posts = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable Integer categoryId){
		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
		return ResponseEntity.ok(posts);
	}
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam (value ="pageNumber", defaultValue =AppConstants.PAGE_NUMBER, required = false)Integer pageNumber,
			@RequestParam (value ="pageSize", defaultValue =AppConstants.PAGE_SIZE, required = false)Integer pageSize,
			@RequestParam (value ="sortBy", defaultValue=AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam (value="sortByDirection", defaultValue =AppConstants.SORT_DIRECTION, required = false ) String sortByDirection){
		return new ResponseEntity<PostResponse>(this.postService.getAllPosts(pageNumber,pageSize,sortBy, sortByDirection),HttpStatus.OK);
	}
	
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		this.postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(this.postDto,HttpStatus.OK);
	}
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
		this.postDto = this.postService.updatePost(postDto, postId);
		return ResponseEntity.ok(this.postDto);
	}
	
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postDto=this.postService.getPostById(postId);
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully ", true),HttpStatus.OK);
	}
	
	@GetMapping("/post")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@RequestParam(value="keyword") String keyword){
		return new ResponseEntity<List<PostDto>>(this.postService.searchPosts(keyword),HttpStatus.OK);
	}
	
	// post Image upload
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@PathVariable Integer postId, @RequestParam("image") MultipartFile image) throws IOException{
		PostDto postDto = this.postService.getPostById(postId);
		String filename = this.fileService.uploadImage(path, image);
		System.out.println(filename);
		postDto.setImageName(filename);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	

	// Serve Image
	
	@GetMapping(value ="/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
