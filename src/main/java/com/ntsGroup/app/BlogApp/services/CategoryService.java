package com.ntsGroup.app.BlogApp.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntsGroup.app.BlogApp.dto.CategoryDto;
import com.ntsGroup.app.BlogApp.interfaces.CategoryInterface;
import com.ntsGroup.app.BlogApp.model.Category;
import com.ntsGroup.app.BlogApp.repository.CategoryRepository;

@Service
public class CategoryService implements CategoryInterface {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CategoryRepository categoryRepository;

	public CategoryDto addCategory(CategoryDto categoryDto) {

		Category category = modelMapper.map(categoryDto, Category.class);

		Category savedCategory = categoryRepository.save(category);

		return modelMapper.map(savedCategory, CategoryDto.class);

	}

}
