package com.ntsGroup.app.BlogApp.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ntsGroup.app.BlogApp.dto.PostDto;
import com.ntsGroup.app.BlogApp.exceptions.ResourceNotFoundException;
import com.ntsGroup.app.BlogApp.interfaces.PostInterface;
import com.ntsGroup.app.BlogApp.model.Post;
import com.ntsGroup.app.BlogApp.repository.PostRepository;

@Service
public class PostService implements PostInterface {

	@Autowired
	private PostRepository postRepository;

	// Methods
	public PostDto createPost(PostDto postDto) {

		// convert DTO to entity
		Post mappedPost = dtoToMapEntity(postDto);
		Post newPost = postRepository.save(mappedPost);
		// convert entity to DTO
		PostDto mappedDto = mapEntityToDto(newPost);
		return mappedDto;
	}

	public List<PostDto> getPosts() {

		List<Post> posts = postRepository.findAll();
		return posts.stream().map(post -> mapEntityToDto(post)).collect(Collectors.toList());

	}

	public PostDto getPostById(long id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapEntityToDto(post);
	}

	PostDto mapEntityToDto(Post newPost) {

		PostDto postResponse = new PostDto();
		postResponse.setId(newPost.getId());
		postResponse.setContent(newPost.getContent());
		postResponse.setTitle(newPost.getTitle());
		postResponse.setDescription(newPost.getDescription());
		return postResponse;
	}

	Post dtoToMapEntity(PostDto postDto) {

		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		return post;
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());

		Post updatedPost = postRepository.save(post);

		PostDto newPost = mapEntityToDto(updatedPost);
		return newPost;
	}

	@Override
	public void deletePost(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
		
	}

}
