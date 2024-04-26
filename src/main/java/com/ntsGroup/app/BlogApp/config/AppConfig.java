package com.ntsGroup.app.BlogApp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class AppConfig {

//	@Autowired
//	private UserDetailsService userDetailsService;

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
				.anyRequest().authenticated()).httpBasic(Customizer.withDefaults());

		return http.build();
	}
//
//	@Bean
//	UserDetailsService userDetailsService() {
//		UserDetails user = User.builder().username("nitin").password(pwdEncoder().encode("nitin")).roles("USER")
//				.build();
//
//		UserDetails admin = User.builder().username("admin").password(pwdEncoder().encode("admin")).roles("ADMIN")
//				.build();
//
//		return new InMemoryUserDetailsManager(user, admin);
//
//	}

}