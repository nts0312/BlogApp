package com.ntsGroup.app.BlogApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ntsGroup.app.BlogApp.dto.JWTAuthResponse;
import com.ntsGroup.app.BlogApp.dto.LoginDto;
import com.ntsGroup.app.BlogApp.dto.RegisterDto;
import com.ntsGroup.app.BlogApp.interfaces.AuthInterface;
import com.ntsGroup.app.BlogApp.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthInterface authInterface;

	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {
		String response = authInterface.login(loginDto);

		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(response);
		return ResponseEntity.ok(jwtAuthResponse);
	}

	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
		String response = authInterface.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}
}
