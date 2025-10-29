package com.pedrovisk.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

class ExceptionHandlerAdviceTest {

    private final ExceptionHandlerAdvice handler = new ExceptionHandlerAdvice();

    private ResponseEntity<String> invokePrivate(String methodName, Exception ex) {
        return ReflectionTestUtils.invokeMethod(handler, methodName, ex);
    }

    @Test
    void shouldHandleUserNotFoundException() {
        ResponseEntity<String> response =
                invokePrivate("userNotFound", new UserNotFoundException("Custom user not found"));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Custom user not found", response.getBody());
    }

    @Test
    void shouldHandleRecordNotFoundException() {
        ResponseEntity<String> response =
                invokePrivate("recordNotFound", new RecordNotFoundException("Record missing"));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Record missing", response.getBody());
    }

    @Test
    void shouldHandleRuntimeException() {
        ResponseEntity<String> response =
                invokePrivate("handleAllExceptions", new RuntimeException("Something went wrong"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("The Server had an Error processing your request. Please try again later.", response.getBody());
    }

    @Test
    void shouldHandleInsufficientBalanceException() {
        ResponseEntity<String> response =
                invokePrivate("handleAllExceptions", new InsufficientBalanceException("Not enough balance"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Not enough balance", response.getBody());
    }

    @Test
    void shouldHandleArithmeticException() {
        ResponseEntity<String> response =
                invokePrivate("handleArithmeticException", new ArithmeticException("Divide by zero"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Divide by zero", response.getBody());
    }

    @Test
    void shouldHandleOperationNotFoundException() {
        ResponseEntity<String> response =
                invokePrivate("handleAllExceptions", new OperationNotFoundException("Operation does not exist"));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Operation does not exist", response.getBody());
    }
}