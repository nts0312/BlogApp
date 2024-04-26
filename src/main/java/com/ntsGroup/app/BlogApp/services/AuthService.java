package com.ntsGroup.app.BlogApp.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ntsGroup.app.BlogApp.dto.LoginDto;
import com.ntsGroup.app.BlogApp.dto.RegisterDto;
import com.ntsGroup.app.BlogApp.exceptions.BlogAPIExceptions;
import com.ntsGroup.app.BlogApp.interfaces.AuthInterface;
import com.ntsGroup.app.BlogApp.model.Role;
import com.ntsGroup.app.BlogApp.model.User;
import com.ntsGroup.app.BlogApp.repository.RoleRepository;
import com.ntsGroup.app.BlogApp.repository.UserRepository;
import com.ntsGroup.app.BlogApp.security.JwtTokenProvider;

@Service
public class AuthService implements AuthInterface {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	public String login(LoginDto loginDto) {
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String token = jwtTokenProvider.generateToken(auth);
		return token;
	}

	public String register(RegisterDto registerDto) {

		// add check for username exists in db
		if (userRepository.existsByUsername(registerDto.getUsername())) {
			throw new BlogAPIExceptions(HttpStatus.BAD_REQUEST, "Username is already present");
		}

		// add check for email exists in db
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new BlogAPIExceptions(HttpStatus.BAD_REQUEST, "email is already present");
		}

		User user = new User();
		user.setEmail(registerDto.getEmail());
		user.setUsername(registerDto.getUsername());
		user.setName(registerDto.getName());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Set<Role> roles = new HashSet<>();
		Role role = roleRepository.findByName("ROLE_USER").getFirst();

		roles.add(role);
		user.setRoles(roles);

		userRepository.save(user);

		return "User registered sucessfully";
	}

}
