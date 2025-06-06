package com.talkovia.infra.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestErrorMessage {
	private final int status;
	private final String message;
	private String stackTrace;
	private List<ValidationError> errors;

	@Getter
	@Setter
	@RequiredArgsConstructor
	private static class ValidationError {
		private final String field;
		private final String message;
	}

	public void addValidationError(String field, String message) {
		if (errors == null) {
			errors = new ArrayList<>();
		}
		errors.add(new ValidationError(field, message));
	}
}
