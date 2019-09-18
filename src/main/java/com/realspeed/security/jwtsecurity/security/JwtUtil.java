package com.realspeed.security.jwtsecurity.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.realspeed.security.jwtsecurity.model.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	public static final String BEARER_TOKEN = "Bearer ";

	@Value("${jwt.client.secret}")
	private String secret;

	//30 Sec
	public static final long JWT_TOKEN_VALIDITY = 30;

	/**
	 * Generate the JWT Token
	 * 
	 * @param jwtUser
	 * @return
	 */
	public String generate(JwtUser jwtUser) {
		Claims claims = Jwts.claims().setSubject(jwtUser.getSubject());
		claims.setIssuedAt(new Date(System.currentTimeMillis()));
		claims.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000));
		claims.put("userId", String.valueOf(jwtUser.getId()));
		claims.put("role", jwtUser.getRole());

		return BEARER_TOKEN + Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	/**
	 * Validate the JWT Token
	 * 
	 * @param token
	 * @return
	 */
	public JwtUser validate(String token) {
		JwtUser jwtUser = null;
		try {
			Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			Date expiration = body.getExpiration();
			if (expiration.before(new Date())) {
				throw new RuntimeException("JWT token is not valid");
			}
			jwtUser = new JwtUser();
			jwtUser.setSubject(body.getSubject());
			jwtUser.setId(Long.parseLong((String) body.get("userId")));
			jwtUser.setRole((String) body.get("role"));
			return jwtUser;
		} catch (Exception e) {
			throw new RuntimeException("JWT token is not valid");
		}
	}
}
