package com.gopal.blog.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Component
public class Post {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer postId;
	@Column(name="post_title",length=100)
	private String title;
	@Column(length=1000)
	private String content;
	private String imageName;
	private Date addedDate;
	@ManyToOne
	private Category category;
	@ManyToOne
	private User user;
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Comment> comment = new HashSet<>();
	
}
