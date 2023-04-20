package com.gopal.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopal.blog.Repositories.CommentRepo;
import com.gopal.blog.Repositories.PostRepo;
import com.gopal.blog.entities.Comment;
import com.gopal.blog.entities.Post;
import com.gopal.blog.exceptions.ResourceNotFoundException;
import com.gopal.blog.payloads.CommentDto;
import com.gopal.blog.services.CommentService;

@Service
public class CommentSeviceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));;
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","Id",commentId));
		this.commentRepo.delete(comment);
	}

}
