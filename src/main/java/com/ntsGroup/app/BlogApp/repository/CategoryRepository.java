package com.ntsGroup.app.BlogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ntsGroup.app.BlogApp.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
