package com.ntsGroup.app.BlogApp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ntsGroup.app.BlogApp.dto.PostDto;
import com.ntsGroup.app.BlogApp.dto.PostResponse;
import com.ntsGroup.app.BlogApp.exceptions.ResourceNotFoundException;
import com.ntsGroup.app.BlogApp.interfaces.PostInterface;
import com.ntsGroup.app.BlogApp.model.Post;
import com.ntsGroup.app.BlogApp.repository.PostRepository;

@Service
public class PostService implements PostInterface {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper mapper;

	// Methods
	public PostDto createPost(PostDto postDto) {

		// convert DTO to entity
		Post mappedPost = dtoToMapEntity(postDto);
		Post newPost = postRepository.save(mappedPost);
		// convert entity to DTO
		PostDto mappedDto = mapEntityToDto(newPost);
		return mappedDto;
	}

	public PostDto getPostById(long id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapEntityToDto(post);
	}

	PostDto mapEntityToDto(Post newPost) {
		PostDto postResponse = mapper.map(newPost, PostDto.class);
		return postResponse;
	}

	Post dtoToMapEntity(PostDto postDto) {
		Post post = mapper.map(postDto, Post.class);
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

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String dir) {

		Sort sort = dir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
		Page<Post> posts = postRepository.findAll(pageRequest);
		List<Post> listOfPosts = posts.getContent();

		List<PostDto> postsList = listOfPosts.stream().map(post -> mapEntityToDto(post)).collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();

		postResponse.setPostList(postsList);
		postResponse.setLast(posts.isLast());
		postResponse.setPageSize(pageSize);
		postResponse.setPageNo(pageNo);
		postResponse.setTotalPage(posts.getTotalPages());
		postResponse.setTotalElements(posts.getTotalElements());

		return postResponse;

	}

}
