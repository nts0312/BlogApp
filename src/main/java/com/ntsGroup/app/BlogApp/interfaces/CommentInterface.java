package com.ntsGroup.app.BlogApp.interfaces;

import java.util.List;

import com.ntsGroup.app.BlogApp.dto.CommentDto;
import com.ntsGroup.app.BlogApp.model.Comment;

public interface CommentInterface {

	CommentDto createComment(long postId, CommentDto commentDto);

	List<CommentDto> getCommentsByPostId(long postId);

}
