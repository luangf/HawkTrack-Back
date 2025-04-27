package com.talkovia.customexceptions;

import jakarta.persistence.EntityNotFoundException;

public class ObjectNotFoundException extends EntityNotFoundException {
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String message) {
		super(message);
	}
}
