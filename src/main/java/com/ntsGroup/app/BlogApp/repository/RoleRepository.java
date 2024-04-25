package com.ntsGroup.app.BlogApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ntsGroup.app.BlogApp.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	List<Role> findByName(String name);
	
}
