package com.ntsGroup.app.BlogApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ntsGroup.app.BlogApp.dto.CategoryDto;
import com.ntsGroup.app.BlogApp.interfaces.CategoryInterface;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

	@Autowired
	private CategoryInterface categoryInterface;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {

		CategoryDto savedCategory = categoryInterface.addCategory(categoryDto);
		return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);

	}
}
