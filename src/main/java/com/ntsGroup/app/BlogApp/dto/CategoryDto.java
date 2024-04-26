package com.ntsGroup.app.BlogApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CategoryDto {

	private Long id;
	private String name;
	private String description;
}
