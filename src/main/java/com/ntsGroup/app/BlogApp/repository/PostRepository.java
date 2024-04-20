package com.ntsGroup.app.BlogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ntsGroup.app.BlogApp.dto.PostDto;
import com.ntsGroup.app.BlogApp.model.Post;

//no need for Repository annotation as jpa rep. already implemented it
public interface PostRepository extends JpaRepository<Post, Long>{
	
	
}
