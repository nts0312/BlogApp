package com.ntsGroup.app.BlogApp.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ntsGroup.app.BlogApp.security.JwtAuthenticationEntryPoint;
import com.ntsGroup.app.BlogApp.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class AppConfig {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	PasswordEncoder pwdEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();

	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests((authorize) ->

		authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll().requestMatchers("/api/auth/**").permitAll()
				.requestMatchers("/v3/api-docs/**").permitAll().requestMatchers("/swagger-ui/**").permitAll().anyRequest()
				.authenticated()).httpBasic(Customizer.withDefaults())
				.exceptionHandling((ex) -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}