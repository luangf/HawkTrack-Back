package com.talkovia.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import com.talkovia.dto.auth.ForgotRequestDTO;
import com.talkovia.dto.auth.LoginRegisterResponseDTO;
import com.talkovia.dto.auth.LoginRequestDTO;
import com.talkovia.dto.auth.RegisterRequestDTO;
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
	public ResponseEntity<LoginRegisterResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
		return ResponseEntity.ok(authService.login(loginRequestDTO, response));
	}

	@Operation(summary = "Register a new user")
	@PostMapping("/register")
	public ResponseEntity<LoginRegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO, HttpServletResponse response) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequestDTO, response));
	}
	
	@Operation(summary = "Forgot password")
	@PostMapping("/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody ForgotRequestDTO forgotRequestDTO) {
		emailService.sendEmail(forgotRequestDTO);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@Operation(summary = "OAuth")
	@GetMapping("/oauth2/success")
	public ResponseEntity<?> oauthSuccess(@AuthenticationPrincipal OAuth2User principal) {
		String email = principal.getAttribute("email");
		return ResponseEntity.ok("Bem-vindo, " + email);
	}

	@Operation(summary = "Logout")
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletResponse response) {
		authService.logout(response);
		return ResponseEntity.ok().build();
	}


}
