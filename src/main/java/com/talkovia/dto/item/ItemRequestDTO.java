package com.talkovia.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ItemRequestDTO(
		@NotBlank(message = "Item name is obrigatory")
		@Size(min = 1, max = 100, message = "Item name must be between 1 and 100 characters long")
		String name,
		@Size(max = 750, message = "Item description max value is 750 characters long")
		String description) {}
