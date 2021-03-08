package com.observepoint.test.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ExceptionsAdvice {

	@ResponseBody
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String resourceNotFoundHandler(ResourceNotFoundException ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(DBException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	String dbErrorHandler(DBException ex) {
		return ex.getMessage();
	}
}
