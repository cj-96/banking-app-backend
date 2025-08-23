package com.example.auth.exceptionhandling;

public class InvalidCustomerIdException extends RuntimeException {

    public InvalidCustomerIdException(String message) {
        super(message);
    }

    public InvalidCustomerIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
