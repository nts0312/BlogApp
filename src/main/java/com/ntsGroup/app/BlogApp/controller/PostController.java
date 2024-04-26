package com.ntsGroup.app.BlogApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ntsGroup.app.BlogApp.dto.PostDto;
import com.ntsGroup.app.BlogApp.dto.PostResponse;
import com.ntsGroup.app.BlogApp.interfaces.PostInterface;
import com.ntsGroup.app.BlogApp.services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostInterface postInterface;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
		return new ResponseEntity<>(postInterface.createPost(postDto), HttpStatus.CREATED);
	}

	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

		return postInterface.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {
		return new ResponseEntity<>(postInterface.getPostById(id), HttpStatus.OK);
	}

	@PostMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id) {
		return new ResponseEntity<>(postInterface.updatePost(postDto, id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
		postInterface.deletePost(id);
		return new ResponseEntity<>("Post entity deleted successfully !", HttpStatus.OK);
	}
}
