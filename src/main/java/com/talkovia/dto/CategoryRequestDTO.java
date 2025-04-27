package com.talkovia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(
		@NotBlank(message = "Category name is obrigatory")
		@Size(min = 1, max = 100, message = "Category name must be between 1 and 100 characters long")
		String name,
		@Size(max = 250, message = "Category description max value is 250 characters long")
		String description) {}
