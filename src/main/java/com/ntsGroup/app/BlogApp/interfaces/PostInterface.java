package com.ntsGroup.app.BlogApp.interfaces;

import com.ntsGroup.app.BlogApp.dto.PostDto;
import com.ntsGroup.app.BlogApp.dto.PostResponse;

public interface PostInterface {

	PostDto createPost(PostDto postDto);

	PostResponse getAllPosts(int pageNo, int pageSize);

	PostDto getPostById(long id);

	PostDto updatePost(PostDto postDto, long id);

	void deletePost(long id);
}
