package com.talkovia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ForgotRequestDTO(
		@NotBlank(message = "Email is obrigatory")
		@Email(message = "Email invalid")
		@Size(min = 5, max = 254, message = "Email must be between 5 and 254 characters long")
		String email) {

}
