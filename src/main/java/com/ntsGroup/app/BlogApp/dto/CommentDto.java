package com.ntsGroup.app.BlogApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

	private long id;

	@NotEmpty(message = "Name should not be empty")
	private String name;

	@Email
	@NotEmpty(message = "Email should not be empty")
	private String email;

	@NotEmpty
	@Size(min = 1, message = "Comment Body  should have at least 1 character")
	private String body;
}
