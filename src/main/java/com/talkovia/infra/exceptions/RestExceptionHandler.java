package com.talkovia.infra.exceptions;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.talkovia.customexceptions.ObjectNotFoundException;
import com.talkovia.customexceptions.UserAlreadyExistsException;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	@Value("${server.error.include-exception}")
	private boolean printStackTrace;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException methodArgumentNotValidException,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request) {
		RestErrorMessage restErrorMessage = new RestErrorMessage(
				HttpStatus.UNPROCESSABLE_ENTITY.value(),
				"Validation error. Check 'errors' field for details.");
		for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
			restErrorMessage.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return ResponseEntity.unprocessableEntity().body(restErrorMessage);
	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllUncaughtException(
            Exception exception,
            WebRequest request) {
        final String errorMessage = "Unknown error occurred";
        log.error(errorMessage, exception);
        return buildErrorResponse(
                exception,
                errorMessage,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }
	
	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException dataIntegrityViolationException,
            WebRequest request) {
        String errorMessage = dataIntegrityViolationException.getMostSpecificCause().getMessage();
        log.error("Failed to save entity with integrity problems: " + errorMessage, dataIntegrityViolationException);
        return buildErrorResponse(
                dataIntegrityViolationException,
                errorMessage,
                HttpStatus.CONFLICT,
                request);
    }
	
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException constraintViolationException,
            WebRequest request) {
        log.error("Failed to validate element", constraintViolationException);
        return buildErrorResponse(
                constraintViolationException,
                HttpStatus.UNPROCESSABLE_ENTITY,
                request);
    }
    
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleObjectNotFoundException(
            ObjectNotFoundException objectNotFoundException,
            WebRequest request) {
        log.error("Failed to find the requested element", objectNotFoundException);
        return buildErrorResponse(
                objectNotFoundException,
                HttpStatus.NOT_FOUND,
                request);
    }
    
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(
    		UserAlreadyExistsException userAlreadyExistsException,
            WebRequest request) {
        log.error("Failed to find the requested element", userAlreadyExistsException);
        return buildErrorResponse(
        		userAlreadyExistsException,
                HttpStatus.CONFLICT,
                request);
    }
	
	private ResponseEntity<Object> buildErrorResponse(
            Exception exception,
            String message,
            HttpStatus httpStatus,
            WebRequest request) {
		RestErrorMessage errorResponse = new RestErrorMessage(httpStatus.value(), message);
        if (this.printStackTrace) {
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
	
    private ResponseEntity<Object> buildErrorResponse(
            Exception exception,
            HttpStatus httpStatus,
            WebRequest request) {
        return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
    }
	
	/*
	@ExceptionHandler(InvalidCredentialsException.class)
	private ResponseEntity<RestErrorMessage> invalidCredentialsHandler(InvalidCredentialsException e) {
		RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
	}

	@ExceptionHandler(RuntimeException.class)
	private ResponseEntity<RestErrorMessage> runtimeHandler(RuntimeException e) {
		RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
	}
	*/
}
