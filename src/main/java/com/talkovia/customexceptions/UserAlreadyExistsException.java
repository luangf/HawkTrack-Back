package com.talkovia.customexceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class UserAlreadyExistsException extends DataIntegrityViolationException{
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
