package com.ntsGroup.app.BlogApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ntsGroup.app.BlogApp.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	List<Comment> findByPostId(long postId);
}
