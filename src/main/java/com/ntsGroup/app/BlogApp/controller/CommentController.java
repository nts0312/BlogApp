package com.ntsGroup.app.BlogApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ntsGroup.app.BlogApp.dto.CommentDto;
import com.ntsGroup.app.BlogApp.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long id,
			@RequestBody CommentDto commetnDto) {

		return new ResponseEntity<>(commentService.createComment(id, commetnDto), HttpStatus.CREATED);
	}

	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") long postId) {
		return commentService.getCommentsByPostId(postId);
	}

	@GetMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "id") Long commentId) {
		return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
	}

	@PostMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updatComment(@PathVariable(value = "") long postId,
			@PathVariable(value = "id") long commentId, @RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);

	}

	@DeleteMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") long postId,
			@PathVariable(value = "id") Long commentId) {
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<>("Comment deleted successfully !", HttpStatus.OK);
	}
}