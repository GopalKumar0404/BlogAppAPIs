package com.gopal.blog.payloads;

import com.gopal.blog.entities.Post;
import com.gopal.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {

	private Integer id;
	private String content;
	
}
