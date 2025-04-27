package com.talkovia.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkovia.dto.ForgotRequestDTO;
import com.talkovia.dto.LoginRegisterResponseDTO;
import com.talkovia.dto.LoginRequestDTO;
import com.talkovia.dto.RegisterRequestDTO;
import com.talkovia.services.AuthService;
import com.talkovia.services.EmailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthController {
	private final AuthService authService;
	private final EmailService emailService;  
	
	public AuthController(AuthService authService, EmailService emailService) {
		this.authService=authService;
		this.emailService=emailService;
	}
	
	@Operation(summary = "Login an existing user")
	@PostMapping("/login")
	public ResponseEntity<LoginRegisterResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
		return ResponseEntity.ok(authService.login(loginRequestDTO));
	}

	@Operation(summary = "Register a new user")
	@PostMapping("/register")
	public ResponseEntity<LoginRegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequestDTO));
	}
	
	@Operation(summary = "Forgot password")
	@PostMapping("/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody ForgotRequestDTO forgotRequestDTO) {
		emailService.sendEmail(forgotRequestDTO);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
