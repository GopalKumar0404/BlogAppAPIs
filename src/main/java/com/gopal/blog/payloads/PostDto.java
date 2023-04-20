package com.gopal.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.gopal.blog.entities.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
public class PostDto {

	private Integer postId;
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;
	
	private UserDto user;
	private CategoryDto category;
	
	private Set<CommentDto> comment = new HashSet<>();
}
