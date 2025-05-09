package com.talkovia.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.talkovia.customexceptions.InvalidCredentialsException;
import com.talkovia.customexceptions.ObjectNotFoundException;
import com.talkovia.customexceptions.UserAlreadyExistsException;
import com.talkovia.dto.auth.LoginRegisterResponseDTO;
import com.talkovia.dto.auth.LoginRequestDTO;
import com.talkovia.dto.auth.RegisterRequestDTO;
import com.talkovia.model.User;
import com.talkovia.repositories.UserRepository;
import com.talkovia.security.TokenService;

@Service
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;
	private final CookieService cookieService;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService, CookieService cookieService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenService = tokenService;
		this.cookieService = cookieService;
	}

	public LoginRegisterResponseDTO login(LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
		User user = userRepository.findByEmail(loginRequestDTO.email())
				.orElseThrow(() -> new ObjectNotFoundException("Email not found"));
		if (passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())) {
			String token = tokenService.generateToken(user);

			cookieService.generateCookieWithJWT(token, response);

			return new LoginRegisterResponseDTO(user.getUsername());
		}
		throw new InvalidCredentialsException("Invalid Credentials");
	}

	public LoginRegisterResponseDTO register(RegisterRequestDTO registerRequestDTO, HttpServletResponse response) {
		if (userRepository.findByEmail(registerRequestDTO.email()).isPresent()) {
			throw new UserAlreadyExistsException("Email already in use");
		}
		if (userRepository.findByUsername(registerRequestDTO.username()).isPresent()) {
			throw new UserAlreadyExistsException("Username already in use");
		}

		User newUser = new User();
		newUser.setEmail(registerRequestDTO.email());
		newUser.setUsername(registerRequestDTO.username());
		newUser.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
		userRepository.save(newUser);

		String token = tokenService.generateToken(newUser);

		cookieService.generateCookieWithJWT(token, response);

		return new LoginRegisterResponseDTO(newUser.getUsername());
	}

	public void logout(HttpServletResponse response){
		cookieService.expirateCookie(response);
	}
}
