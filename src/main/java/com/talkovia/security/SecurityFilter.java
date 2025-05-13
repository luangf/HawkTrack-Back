package com.talkovia.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.talkovia.model.User;
import com.talkovia.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	private final TokenService tokenService;
	private final UserRepository userRepository;

	public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService=tokenService;
		this.userRepository=userRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token = recoverTokenCookie(request);
		if (token != null) {
			var email = tokenService.validateToken(token);
			Optional<User> user = userRepository.findByEmail(email);
			if(user.isPresent()){
				var authentication = new UsernamePasswordAuthenticationToken(user, null, user.get().getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
	}

	//I used before change to cookie http only, getting from authorization
	private String recoverTokenAuthorization(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if (authHeader == null) return null;
		return authHeader.replace("Bearer ", "");
	}

	private String recoverTokenCookie(HttpServletRequest request) {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if ("auth-token".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
