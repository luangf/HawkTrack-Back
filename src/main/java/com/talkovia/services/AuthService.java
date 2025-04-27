package com.talkovia.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.talkovia.customexceptions.InvalidCredentialsException;
import com.talkovia.customexceptions.ObjectNotFoundException;
import com.talkovia.customexceptions.UserAlreadyExistsException;
import com.talkovia.dto.LoginRegisterResponseDTO;
import com.talkovia.dto.LoginRequestDTO;
import com.talkovia.dto.RegisterRequestDTO;
import com.talkovia.model.User;
import com.talkovia.repositories.UserRepository;
import com.talkovia.security.TokenService;

@Service
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenService = tokenService;
	}

	public LoginRegisterResponseDTO login(LoginRequestDTO loginRequestDTO) {
		User user = userRepository.findByEmail(loginRequestDTO.email())
				.orElseThrow(() -> new ObjectNotFoundException("Email não encontrado"));
		if (passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())) {
			String token = tokenService.generateToken(user);
			return new LoginRegisterResponseDTO(user.getUsername(), token);
		}
		throw new InvalidCredentialsException("Invalid Credentials");
	}

	public LoginRegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {
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
		return new LoginRegisterResponseDTO(newUser.getUsername(), token);
	}
}
