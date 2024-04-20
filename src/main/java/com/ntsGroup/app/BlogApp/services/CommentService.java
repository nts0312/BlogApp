package com.ntsGroup.app.BlogApp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ntsGroup.app.BlogApp.dto.CommentDto;
import com.ntsGroup.app.BlogApp.dto.PostDto;
import com.ntsGroup.app.BlogApp.exceptions.BlogAPIExceptions;
import com.ntsGroup.app.BlogApp.exceptions.ResourceNotFoundException;
import com.ntsGroup.app.BlogApp.interfaces.CommentInterface;
import com.ntsGroup.app.BlogApp.model.Comment;
import com.ntsGroup.app.BlogApp.model.Post;
import com.ntsGroup.app.BlogApp.repository.CommentRepository;
import com.ntsGroup.app.BlogApp.repository.PostRepository;

@Service
public class CommentService implements CommentInterface {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper mapper;

	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment = dtoToMapEntity(commentDto);

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// set post to comment Entity
		comment.setPost(post);
		// comment entity to DB
		Comment newComment = commentRepository.save(comment);
		return mapEntityToDto(newComment);

	}

	CommentDto mapEntityToDto(Comment comment) {
		CommentDto commentResponse = mapper.map(comment, CommentDto.class);
		return commentResponse;
	}

	Comment dtoToMapEntity(CommentDto commentDto) {
		Comment comment = mapper.map(commentDto, Comment.class);
		return comment;
	}

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);
		return comments.stream().map(comment -> mapEntityToDto(comment)).collect(Collectors.toList());
	}

	public CommentDto getCommentById(Long postId, Long commentId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIExceptions(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}

		return mapEntityToDto(comment);
	}

	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIExceptions(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}

		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());
		comment.setName(commentRequest.getName());

		Comment updateComment = commentRepository.save(comment);

		return mapEntityToDto(updateComment);
	}

	public void deleteComment(Long postId, Long commentId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIExceptions(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}

		commentRepository.delete(comment);
	}

}
