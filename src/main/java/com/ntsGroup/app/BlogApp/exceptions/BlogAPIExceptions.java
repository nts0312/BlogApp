package com.ntsGroup.app.BlogApp.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class BlogAPIExceptions extends RuntimeException {

	@Getter
	private HttpStatus status;
	private String message;

	public BlogAPIExceptions(HttpStatus status, String message) {

		this.status = status;
		this.message = message;
	}

	public BlogAPIExceptions(HttpStatus status, String message, String mess) {
		super(message);
		this.status = status;
		this.message = mess;
	}

}
