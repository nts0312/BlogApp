package com.ntsGroup.app.BlogApp.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ntsGroup.app.BlogApp.exceptions.BlogAPIExceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${app-jwt-secret}")
	private String jwtSecret;

	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpirationDate;

	// generate JWT token
	public String generateToken(Authentication auth) {

		String username = auth.getName();
		Date currentDate = new Date();
		Date expirDate = new Date(currentDate.getTime() + jwtExpirationDate);
		String token = Jwts.builder().subject(username).issuedAt(new Date()).expiration(expirDate).signWith(key())
				.compact();
		return token;
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	// get username from JWT Token
	public String getUsername(String token) {
		return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();

	}

	// validate JWT Token

	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
			return true;
		} catch (MalformedJwtException e) {
			throw new BlogAPIExceptions(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
		}
		catch (ExpiredJwtException e) {
			throw new BlogAPIExceptions(HttpStatus.BAD_REQUEST, "Expired JWT Token");
		}
		catch (UnsupportedJwtException e) {
			throw new BlogAPIExceptions(HttpStatus.BAD_REQUEST, "Unsupported JWT Token");
		}
		catch (IllegalArgumentException e) {
			throw new BlogAPIExceptions(HttpStatus.BAD_REQUEST, "Jwt claims String is null or empty");
		}
		
	}
}
