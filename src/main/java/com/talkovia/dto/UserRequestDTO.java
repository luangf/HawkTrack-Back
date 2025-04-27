package com.talkovia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
	@NotBlank(message = "Email is obrigatory")
	@Email(message = "Email invalid")
	@Size(min = 5, max = 254, message = "Email must be between 5 and 254 characters long")
	String email,
	@NotBlank(message = "Username is obrigatory")
	@Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters long")
	String username,
	@NotBlank(message = "Password is obrigatory")
	@Size(min = 12, max = 140, message = "Password must be between 12 and 140 characters long")
	String password) {}