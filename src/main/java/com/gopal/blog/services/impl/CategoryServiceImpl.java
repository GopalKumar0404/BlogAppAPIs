package com.gopal.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopal.blog.Repositories.CategoryRepo;
import com.gopal.blog.entities.Category;
import com.gopal.blog.exceptions.ResourceNotFoundException;
import com.gopal.blog.payloads.CategoryDto;
import com.gopal.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryDto updatedCategoryDto;
	
	@Autowired
	Category category;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		category = this.modelMapper.map(categoryDto, Category.class);
		updatedCategoryDto = this.modelMapper.map(this.categoryRepo.save(category), CategoryDto.class);
		return updatedCategoryDto;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		this.category =this.categoryRepo.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("Category","Id",categoryId));
		categoryDto.setCategoryId(categoryId);
		updatedCategoryDto = this.modelMapper.map(
				categoryRepo.save(this.modelMapper.map(categoryDto, Category.class)),CategoryDto.class);
//		category.setCategoryTitle(categoryDto.getCategoryTitle());
//		category.setCategoryDescription(categoryDto.getCategoryDescription());
//		updatedCategoryDto = this.modelMapper.map(this.categoryRepo.save(category), CategoryDto.class);
		return updatedCategoryDto;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		this.category =this.categoryRepo.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("Category","Id",categoryId));
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		this.category = this.categoryRepo.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("Category","Id",categoryId));
		this.updatedCategoryDto = this.modelMapper.map(category,CategoryDto.class);
		return updatedCategoryDto;
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> categoryDtos = categories.stream().map(category-> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return categoryDtos;
	}

}
