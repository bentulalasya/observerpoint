package com.observepoint.test.test.exception;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String resource, Long id) {
		super(String.format("Can not find the %s with id %d", resource, id));
	} 
}
