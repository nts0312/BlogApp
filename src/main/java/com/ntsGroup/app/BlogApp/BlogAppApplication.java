package com.ntsGroup.app.BlogApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Spring Boot Blog App REST API's", description = "Spring Boot Blog App REST API's Documentation", version = "v1.0", contact = @Contact(name = "Nitin", email = "nitinsinghm111@gmail.com")))

public class BlogAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(BlogAppApplication.class, args);
	}

}
