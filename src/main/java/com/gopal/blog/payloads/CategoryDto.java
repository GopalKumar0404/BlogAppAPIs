package com.gopal.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class CategoryDto {

	private Integer categoryId;
	@NotBlank
	@Size(min=4 ,max=25)
	private String categoryTitle;
	@NotBlank
	@Size(min=10, max=250)
	private String categoryDescription;
}
