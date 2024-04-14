package com.ntsGroup.app.BlogApp.interfaces;

import java.util.List;

import com.ntsGroup.app.BlogApp.dto.PostDto;

public interface PostInterface {
	
	PostDto createPost(PostDto postDto);
	List<PostDto> getPosts();
	PostDto getPostById(long id);
	PostDto updatePost(PostDto postDto, long id);
	void deletePost(long id);
}
