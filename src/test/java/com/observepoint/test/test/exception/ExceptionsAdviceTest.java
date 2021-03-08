package com.observepoint.test.test.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ExceptionsAdviceTest {

	private final ExceptionsAdvice exceptionsAdvice = new ExceptionsAdvice();

	@Test
	void resourceNotFoundHandlerTest() {
		assertEquals("Can not find the test with id 0", exceptionsAdvice.resourceNotFoundHandler(new ResourceNotFoundException("test", 0L)));
	}

	@Test
	void dbErrorHandlerTest() {
		assertEquals("message", exceptionsAdvice.dbErrorHandler(new DBException("message")));
	}

}
