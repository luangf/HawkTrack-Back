package com.talkovia.dto.category;

import com.talkovia.model.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CategoryRequestDTO(
		@NotBlank(message = "Category name is obrigatory")
		@Size(min = 1, max = 100, message = "Category name must be between 1 and 100 characters long")
		String name,

		@Size(max = 750, message = "Category description max value is 750 characters long")
		String description
		) {}
