package com.talkovia.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.talkovia.model.User;
import com.talkovia.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {
	private final UserRepository userRepository;

	public AuthorizationService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username);
	}

}
